import {Injectable} from '@angular/core';
import {WebSocketService} from "./web-socket.service";
import {HttpClient} from "@angular/common/http";
import {TokenStorageService} from "./token-storage.service";
import {BehaviorSubject, Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {map} from "rxjs/operators";
import {LuckyWheelModel} from "../_dtos/lucky-wheel/LuckyWheelModel";
import {ResultData} from "../_dtos/common/ResultData";

@Injectable()
export class LuckyWheelService extends WebSocketService {

  nbNotificationWheel: Observable<LuckyWheelModel[]>
  private myNbNotificationWheel: BehaviorSubject<LuckyWheelModel[]> = new BehaviorSubject([])

  constructor(private httpClient: HttpClient, protected tokenStorageService: TokenStorageService) {
    super(tokenStorageService)
    this.nbNotificationWheel = this.myNbNotificationWheel.asObservable()
  }

  findFirstByCurrentDateTime(): Observable<ResultData> {
    return this.httpClient.get(`${environment.DOMAIN}/api/lucky-wheel`) as Observable<ResultData>
  }

  spin(groupPrizeId: number): Observable<ResultData> {
    return this.httpClient.post(`${environment.DOMAIN}/api/lucky-wheel/spin/${groupPrizeId}`, null)
      .pipe(map((resultData: ResultData) => {
        return resultData
      }))
  }

  onLuckyWheelReceived(resultData: any) {
    let data =  JSON.parse(resultData.body)['data'] as LuckyWheelModel
    this.myNbNotificationWheel.value.push(data)
    this.myNbNotificationWheel.next( this.myNbNotificationWheel.value)
  }

}
