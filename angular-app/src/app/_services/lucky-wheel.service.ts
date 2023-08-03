import {Injectable} from '@angular/core';
import {WebSocketService} from "./web-socket.service";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {TokenStorageService} from "./token-storage.service";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {LuckyWheelModel} from "../_dtos/lucky-wheel/LuckyWheelModel";
import {PrizeGroup} from "../_dtos/lucky-wheel/PrizeGroup";

@Injectable()
export class LuckyWheelService extends WebSocketService {

  constructor(private httpClient: HttpClient, protected tokenStorageService: TokenStorageService) {
    super(tokenStorageService)
  }

  findFirstByCurrentDateTime(): Observable<PrizeGroup> {
    return this.httpClient.get(`${environment.DOMAIN}/api/lucky-wheel`)
      .pipe(map((model: PrizeGroup) => {
        return model
      }))
  }

  spin(groupPrizeId: number): Observable<LuckyWheelModel> {
    return this.httpClient.post(`${environment.DOMAIN}/api/lucky-wheel/spin/${groupPrizeId}`,null)
      .pipe(map((model: LuckyWheelModel) => {
        this.updateNotificationLucky(model)
        return model
      }))
  }

  onLuckyWheelReceived(luckyWheel: any) {
    let json = JSON.parse(luckyWheel.body)
  }

  updateNotificationLucky(model: LuckyWheelModel) {

  }
}
