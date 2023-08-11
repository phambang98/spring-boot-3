import {Injectable} from '@angular/core';
import {Stomp} from "@stomp/stompjs";
import * as SockJS from "sockjs-client";
import {environment} from "../../environments/environment";
import {TokenStorageService} from "./token-storage.service";
import {HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class WebSocketService {

  stompClient: any

  constructor(protected tokenStorageService: TokenStorageService) {
    this.onConnect()
  }

  private onConnect() {
    if (!this.stompClient) {
      this.stompClient = Stomp.over(function () {
        return new SockJS(`${environment.DOMAIN}/ws`);
      });

      this.stompClient.debug = () => {
      };
      const _this = this;
      _this.stompClient.connect({"Authorization": "Bearer " + _this.tokenStorageService.getToken()},
        function (frame) {
          _this.stompClient.subscribe(`/notifications/${_this.tokenStorageService.getUser().id}/queue/messages`, function (sdkEvent) {
            _this.onMessageReceived(sdkEvent);
          });
          _this.stompClient.subscribe(`/notifications/${_this.tokenStorageService.getUser().id}/queue/status`, function (sdkEvent) {
            _this.onStatusReceived(sdkEvent);
          });
          _this.stompClient.subscribe(`/notifications/lucky-wheel`, function (sdkEvent) {
            _this.onLuckyWheelReceived(sdkEvent);
          });
        }, function (error) {
          setTimeout(() => _this.onConnect(), 5000);
        });
    }
  }

  onMessageReceived(message: any) {

  }

  onStatusReceived(status: any) {

  }

  onLuckyWheelReceived(luckyWheel: any) {

  }
}
