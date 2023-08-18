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
    this.renderer2.listen(this.inner.nativeElement, 'transitionend', () => {
      // this.dialogService.open(DialogLuckyWheelComponent, {
      //   context: {title: "Thông báo", message: this.message}
      // }).onClose.subscribe(() => {
        this.activeBtn = true
      // })
    })
  }

  spin() {
    this.activeBtn = false
    let randomNumber = Math.floor(Math.random() * 10)
    this.deg += (randomNumber == 0 ? 10 : randomNumber) * 360;
    this.renderer2.setStyle(this.inner.nativeElement, "transform", `rotate(${this.deg}deg)`)
    this.luckyWheelService.spin(this.prizeGroupId).subscribe({
      next: (resultData: ResultData) => {
        if (resultData.success) {
          let luckyWheel: LuckyWheelModel = resultData.data
          this.message = luckyWheel.message
          let displayNumber = this.prizes.filter(x => x.luckNumber == (luckyWheel.luckyNumber ? luckyWheel.spinNumber : null))[0].displayNumber
          let numberPresent = Math.floor(this.deg / 6)
          do {
            numberPresent = Math.floor(numberPresent / 6)
          } while (numberPresent > 6)
          //  nếu ô hiện tại là 3 ô cần đến là 3 thì giữ nguyên
          let numberRevolutionMiss = 0
          if (displayNumber == numberPresent) {
            return
          }

          // nếu ô hiện tại là 1 ô cần đến là 5 thì quay thêm 4 ô (5-1)
          if (displayNumber < numberPresent) {
            numberRevolutionMiss = numberPresent - displayNumber
          }
          //  nếu ô hiện tại là 3 ô cần đến là 1 thì quay thêm 4 ô ( 6-3+ ô cần đến)
          if (displayNumber > numberPresent) {
            numberRevolutionMiss = 6 - numberPresent + displayNumber
          }
          if (numberRevolutionMiss != 0) {
            this.deg += (numberRevolutionMiss) * 60
            this.renderer2.setStyle(this.inner.nativeElement, "transform", `rotate(${this.deg}deg)`)
          }
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

