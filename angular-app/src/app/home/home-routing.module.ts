import {NgModule} from '@angular/core';

import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './home.component';
import {AuthGuard} from '../_helpers/auth.guard';
import {ChatComponent} from '../chat/chat.component';
import {ChatBannerComponent} from '../chat/chat-banner/chat-banner.component';
import {ProfileComponent} from '../profile/profile.component';
import {LuckyWheelComponent} from "../lucky-wheel/lucky-wheel.component";
import {ChatDetailComponent} from "../chat/chat-detail/chat-detail.component";

const routes: Routes = [
  {
    path: '', component: HomeComponent, canActivate: [AuthGuard], children: [
      {
        path: 'chat', component: ChatComponent, children: [
          {path: '', component: ChatBannerComponent},
          {path: ':chatId', component: ChatDetailComponent},
          {path: 'profile-friend', component: ProfileComponent},
        ]
      },
      {path: 'profile', component: ProfileComponent},
      {path: 'lucky-wheel', component: LuckyWheelComponent}
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class HomeRoutingModule {

}
