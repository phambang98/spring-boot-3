import {Component} from '@angular/core';
import {Subscription} from "rxjs";
import {NotificationService} from "../../../_services/notification.service";
import {Notification, NotificationType} from "../../../_dtos/notification/notification";

@Component({
  selector: 'app-notification',
  template: `
    <div class="notifications">
      <div *ngFor="let notification of notifications" class="notification" [ngClass]="className(notification)">
        <ng-container *ngTemplateOutlet="notificationTpl;context:{notification:notification}"></ng-container>
      </div>
    </div>

    <ng-template #notificationTpl let-notification="notification">
      <div class="title" fxLayout="row" fxLayoutAlign="space-between center">
        <div>{{notification.title}}</div>
        <button nbButton (click)="close(notification)" status="primary">
          <nb-icon icon="close-outline"></nb-icon>
        </button>
      </div>
      <div class="message">{{notification.message}}</div>
    </ng-template>
  `,
  styleUrls: ['./notification-list.component.scss']
})
export class NotificationListComponent {

  notifications: Notification[] = [];
  private subscription: Subscription;

  constructor(private notificationService: NotificationService) {
  }

  private addNotification(notification: Notification) {
    this.close(notification)
    this.notifications.push(notification);
    if (notification.timeout !== 0) {
      setTimeout(() => this.close(notification), notification.timeout);
    }
  }

  ngOnInit() {
    this.subscription = this.notificationService.observable.subscribe(notification => this.addNotification(notification));
  }

  close(notification: Notification) {
    this.notifications = this.notifications.filter(value => value.id !== notification.id);
  }

  className(notification: Notification): string {
    let style: string;
    switch (notification.type) {
      case NotificationType.success:
        style = 'success';
        break;
      case NotificationType.warning:
        style = 'warning';
        break;
      case NotificationType.error:
        style = 'error';
        break;
      default:
        style = 'info';
        break;
    }
    return style;
  }
}
