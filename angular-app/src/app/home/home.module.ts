import {NgModule} from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import {SharedModule} from '../shared/shared.module';

import {HomeComponent} from './home.component';
import {ChatComponent} from '../chat/chat.component';
import {ProfileComponent} from '../profile/profile.component';
import {HomeRoutingModule} from './home-routing.module';
import {ChatListComponent} from '../chat/chat-list/chat-list.component';
import {UserService} from '../_services/user.service';
import {ChatService} from '../_services/chat.service';
import {ChatBannerComponent} from '../chat/chat-banner/chat-banner.component';
import {NewChatComponent} from '../chat/new-chat/new-chat.component';
import {MatProgressBarModule} from '@angular/material/progress-bar';
import {NbMenuService} from "@nebular/theme";
import {MessageService} from "../_services/message.service";
import {WebSocketService} from "../_services/web-socket.service";
import {NotificationService} from "../_services/notification.service";
import {NewGroupComponent} from "../chat/new-group/new-group.component";
import {ReactiveFormsModule} from "@angular/forms";
import {LuckyWheelComponent} from "../lucky-wheel/lucky-wheel.component";
import {ChatDetailComponent} from "../chat/chat-detail/chat-detail.component";
import {LuckyWheelService} from "../_services/lucky-wheel.service";
import {MatTabsModule} from "@angular/material/tabs";
import {AddUserGroupComponent} from "../chat/add-user-group/add-user-group.component";

@NgModule({
  declarations: [
    HomeComponent,
    ChatComponent,
    ProfileComponent,
    ChatListComponent,
    ChatDetailComponent,
    ChatBannerComponent,
    NewChatComponent,
    NewGroupComponent,
    AddUserGroupComponent,
    LuckyWheelComponent
  ],
  imports: [
    CommonModule, HomeRoutingModule, SharedModule, MatProgressBarModule, NgOptimizedImage, ReactiveFormsModule, MatTabsModule
  ],
  exports: [
    LuckyWheelComponent
  ],
  providers: [
    NbMenuService, WebSocketService, UserService, ChatService, MessageService, NotificationService, LuckyWheelService
  ]
})
export class HomeModule {
}
