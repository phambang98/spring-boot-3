import {AfterViewInit, Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import {LuckyWheelService} from "../_services/lucky-wheel.service";
import {PrizeModel} from "../_dtos/lucky-wheel/PrizeModel";
import {LuckyWheelModel} from "../_dtos/lucky-wheel/LuckyWheelModel";
import {ResultData} from "../_dtos/common/ResultData";
import {NbDialogService} from "@nebular/theme";
import {DialogLuckyWheelComponent} from "../shared/dialog/dialog-lucky-wheel/dialog-lucky-wheel.component";

@Component({
  selector: 'app-lucky-wheel',
  templateUrl: './lucky-wheel.component.html',
  styleUrls: ['./lucky-wheel.component.scss']
})
export class LuckyWheelComponent implements OnInit, AfterViewInit {

  activeBtn = true
  prizes: PrizeModel[]
  deg = 0
  prizeGroupId: number
  message: string
  @ViewChild("wheel__inner") inner: ElementRef
  luckyWheelList: LuckyWheelModel []

  constructor(private luckyWheelService: LuckyWheelService, private renderer2: Renderer2, private elementRef: ElementRef,
              private dialogService: NbDialogService) {
    this.luckyWheelService.findFirstByCurrentDateTime().subscribe({
      next: (resultData: ResultData) => {
        if (resultData.success) {
          this.prizes = resultData.data.prizeList
          this.prizeGroupId = resultData.data.id
          this.prizes.map(x => x.deg = 60)
        }
      },
      error: (err: any) => {
        console.log("err-lucky-wheel", err)
      }
    })
    this.luckyWheelService.nbNotificationWheel.subscribe(
      (model: LuckyWheelModel[]) => {
        this.luckyWheelList = model
      }
    )
  }

  ngAfterViewInit() {
    this.renderer2.listen(this.inner.nativeElement, 'transitionend', () => {
        this.dialogService.open(DialogLuckyWheelComponent, {
          context: {title: "Thông báo", message: this.message}
        }).onClose.subscribe(() => {
      this.activeBtn = true
      })
    })
  }

  ngOnInit(): void {
    this.activeBtn = true
  }

  spin() {
    this.activeBtn = false
    let randomNumber = Number((Math.random() * (10) + 10).toFixed(2))
    this.deg += Math.floor(randomNumber * 360);
    this.renderer2.setStyle(this.inner.nativeElement, "transform", `rotate(${this.deg}deg)`)
    this.luckyWheelService.spin(this.prizeGroupId).subscribe({
      next: (resultData: ResultData) => {
        if (resultData.success) {
          let luckyWheel: LuckyWheelModel = resultData.data
          this.message = luckyWheel.message
          let displayNumber = this.prizes.filter(x => x.id == luckyWheel.prizeId)[0].displayNumber
          let numberPresent = this.deg % 360
          if (numberPresent != 0) {
            if (numberPresent > 30 && numberPresent < 90) {
              numberPresent = 6
            } else if (numberPresent > 90 && numberPresent < 150) {
              numberPresent = 5
            } else if (numberPresent > 150 && numberPresent < 210) {
              numberPresent = 4
            } else if (numberPresent > 210 && numberPresent < 270) {
              numberPresent = 3
            } else if (numberPresent > 270 && numberPresent < 330) {
              numberPresent = 2
            } else {
              numberPresent = 1
            }
          } else {
            this.deg += 20
          }
          //  nếu ô hiện tại là 3 ô cần đến là 3 thì giữ nguyên
          if (displayNumber == numberPresent) {
            return
          }
          let numberRevolutionMiss = 0
          // nếu số hiện tại là 5(numberPresent) số cần đến là 3(displayNumber) thì quay thêm 2 số. 4->3
          if (numberPresent > displayNumber) {
            numberRevolutionMiss = numberPresent - displayNumber
          }
          // nếu số hiện tại là 1(numberPresent) cần đến là 5(displayNumber) thì quay thêm 2 số : 6->5
          if (numberPresent < displayNumber) {
            numberRevolutionMiss = 6 - (displayNumber - numberPresent)
          }
          this.deg += numberRevolutionMiss * 60
          this.renderer2.setStyle(this.inner.nativeElement, "transform", `rotate(${this.deg}deg)`)
        } else {
          console.log(resultData.message)
        }
      },
      error: (err: any) => {
        console.log("spin-err", err)
      }
    })
  }

}

