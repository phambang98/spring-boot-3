import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {
  NbLayoutModule,
  NbCardModule,
  NbAlertModule,
  NbInputModule,
  NbCheckboxModule,
  NbFormFieldModule,
  NbButtonModule,
  NbIconModule,
  NbSpinnerModule,
  NbUserModule,
  NbSidebarModule,
  NbChatModule,
  NbListModule,
  NbContextMenuModule,
  NbDialogModule,
  NbProgressBarModule
} from '@nebular/theme';
import {DialogSuccessComponent} from './dialog/dialog-alert/dialog-success.component';
import {ImgFallbackDirective} from './directives/img-fallback.directive';
import {DialogAuthenticationFailureComponent} from "./dialog/dialog-alert/dialog-authentication-failure.component";
import {NotificationListComponent} from './notification/notification/notification-list.component';
import {MatTooltipModule} from "@angular/material/tooltip";
import {MatButtonModule} from "@angular/material/button";
import {MatIconModule} from "@angular/material/icon";


@NgModule({
  declarations: [DialogSuccessComponent, DialogAuthenticationFailureComponent, ImgFallbackDirective, NotificationListComponent],
  imports: [
    CommonModule, NbLayoutModule, NbCardModule, NbAlertModule, NbInputModule, NbFormFieldModule, NbCheckboxModule, NbProgressBarModule,
    NbButtonModule, NbIconModule, NbSpinnerModule, NbUserModule, NbSidebarModule, NbChatModule, NbListModule, NbContextMenuModule,
    NbDialogModule, MatTooltipModule, MatButtonModule, MatIconModule
  ],
  exports: [
    NbLayoutModule, NbCardModule, NbAlertModule, NbInputModule, NbFormFieldModule, NbCheckboxModule, NbButtonModule, NbIconModule,
    NbSpinnerModule, NbUserModule, NbSidebarModule, NbChatModule, NbListModule, NbContextMenuModule, NbDialogModule, NbProgressBarModule,
    DialogSuccessComponent, DialogAuthenticationFailureComponent, NotificationListComponent,
    ImgFallbackDirective,
  ]
})
export class SharedModule {
}
