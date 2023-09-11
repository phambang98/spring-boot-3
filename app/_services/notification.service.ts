import {Injectable} from '@angular/core';
import {Observable, BehaviorSubject} from "rxjs";
import {NotificationType, Notification} from "../_dtos/notification/notification";

@Injectable()
export class NotificationService {

  public observable: Observable<Notification>
  private myObservable: BehaviorSubject<Notification> = new BehaviorSubject(new Notification())

  constructor() {
    this.observable = this.myObservable.asObservable()
  }

  newMessage(title: string, message: string, timeout = 3000) {
    this.myObservable.next(new Notification(0, NotificationType.success, title, message, timeout))
  }

  info(title: string, message: string, timeout = 30000) {
    this.myObservable.next(new Notification(1, NotificationType.info, title, message, timeout))
  }

  warning(title: string, message: string, timeout = 3000) {
    this.myObservable.next(new Notification(2, NotificationType.warning, title, message, timeout))
  }

  error(title: string, message: string, timeout = 0) {
    this.myObservable.next(new Notification(3, NotificationType.error, title, message, timeout))
  }
}
