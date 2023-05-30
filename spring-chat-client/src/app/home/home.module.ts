import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedModule} from '../shared/shared.module';

import {HomeComponent} from './home.component';
import {ChatComponent} from './chat/chat.component';
import {ProfileComponent} from './profile/profile.component';
import {SettingsComponent} from './settings/settings.component';

import {HomeRoutingModule} from './home-routing.module';
import {ChatListComponent} from './chat-list/chat-list.component';
import {UserService} from '../_services/user.service';
import {ChatService} from '../_services/chat.service';
import {LoadingComponent} from './loading/loading.component';
import {ChatDetailComponent} from './chat-detail/chat-detail.component';
import {ChatBannerComponent} from './chat-banner/chat-banner.component';
import {NewChatComponent} from './chat-list/new-chat/new-chat.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {NbMenuService} from "@nebular/theme";
import {MessageService} from "../_services/message.service";
import {WebSocketService} from "../_services/web-socket.service";
import {NotificationService} from "../_services/notification.service";

@NgModule({
  declarations: [
    HomeComponent,
    ChatComponent,
    ProfileComponent,
    SettingsComponent,
    ChatListComponent,
    LoadingComponent,
    ChatDetailComponent,
    ChatBannerComponent,
    NewChatComponent
  ],
  imports: [
    CommonModule, HomeRoutingModule, SharedModule, MatProgressBarModule
  ],
  providers: [
    NbMenuService, WebSocketService, UserService, ChatService, MessageService, NotificationService
  ]
})
export class HomeModule {
}
