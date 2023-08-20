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

  constructor(private luckyWheelService: LuckyWheelService, private renderer2: Renderer2, private elementRef: ElementRef,
              private dialogService: NbDialogService) {


  }

  ngOnInit(): void {
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
  }

  ngAfterViewInit(): void {
    // this.renderer2.listen(this.inner.nativeElement, 'transitionend', () => {
    //   this.dialogService.open(DialogLuckyWheelComponent, {
    //     context: {title: "Thông báo", message: this.message}
    //   }).onClose.subscribe(() => {
      this.activeBtn = true
    //   })
    // })
  }

  spin() {
    this.activeBtn = false
    let randomNumber = Math.random() * 10
    this.deg += Math.floor((randomNumber == 0 ? 10 : randomNumber) * 3600);
    this.renderer2.setStyle(this.inner.nativeElement, "transform", `rotate(${this.deg}deg)`)
    this.luckyWheelService.spin(this.prizeGroupId).subscribe({
      next: (resultData: ResultData) => {
        if (resultData.success) {
          let luckyWheel: LuckyWheelModel = resultData.data
          this.message = luckyWheel.message
          let displayNumber = this.prizes.filter(x => x.id == luckyWheel.prizeId)[0].displayNumber
          if (this.deg % 360 % 60 == 0) {
            this.deg += 20
          }
          let numberPresent = this.deg % 360 / 60
          numberPresent = Math.round(numberPresent)
          while (numberPresent > 6) {
            numberPresent = numberPresent / 6
          }
          numberPresent = Math.round(numberPresent) + 1
          //  nếu ô hiện tại là 3 ô cần đến là 3 thì giữ nguyên
          if (displayNumber == numberPresent) {
            return
          }
          let numberRevolutionMiss = 0
          // nếu số hiện tại là 5(numberPresent) số cần đến là 3(displayNumber) thì quay thêm 2 số. 4->3
          if (numberPresent > displayNumber) {
            numberRevolutionMiss = 6 - (numberPresent - displayNumber)
          }
          // nếu số hiện tại là 3(numberPresent) cần đến là 5(displayNumber) thì quay thêm 4 số : 2->1->6->5
          if (displayNumber > numberPresent) {
            numberRevolutionMiss = displayNumber - numberPresent
          }

          this.deg += numberRevolutionMiss * 60
          this.renderer2.setStyle(this.inner.nativeElement, "transform", `rotate(${this.deg}deg)`)
          this.activeBtn = true
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

