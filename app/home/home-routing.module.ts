import {NgModule} from '@angular/core';

import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home.component';
import {AuthGuard} from '../_helpers/auth.guard';
import {ChatComponent} from '../chat/chat.component';
import {ProfileComponent} from '../profile/profile.component';
import {LuckyWheelComponent} from "../lucky-wheel/lucky-wheel.component";
import {ChatDetailComponent} from "../chat/chat-detail/chat-detail.component";
import {CherryCharmComponent} from "../cherry-charm/cherry-charm.component";
import {LiveStreamComponent} from "../live-stream/live-stream.component";

const routes: Routes = [
  {
    path: '', component: HomeComponent, canActivate: [AuthGuard], children: [
      {
        path: 'chat', component: ChatComponent, children: [
          {path: '', component: ChatDetailComponent},
          {path: ':chatId', component: ChatDetailComponent},
          {path: '/profile-friend', component: ProfileComponent},
        ]
      },
      {path: 'profile', component: ProfileComponent},
      {path: 'lucky-wheel', component: LuckyWheelComponent},
      {path: 'cherry-charm', component: CherryCharmComponent},
      {path: 'slot-machine', component: CherryCharmComponent},
      {path: 'live-stream', component: LiveStreamComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomeRoutingModule {

}
