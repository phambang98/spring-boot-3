import {Component, ElementRef, OnInit, Renderer2, ViewChild} from '@angular/core';
import * as $ from 'jquery';
import {LuckyWheelService} from "../_services/lucky-wheel.service";
import {UserProfile} from "../_dtos/user/UserProfile";
import {PrizeModel} from "../_dtos/lucky-wheel/PrizeModel";
import {PrizeGroup} from "../_dtos/lucky-wheel/PrizeGroup";
import {LuckyWheelModel} from "../_dtos/lucky-wheel/LuckyWheelModel";

@Component({
  selector: 'app-lucky-wheel',
  templateUrl: './lucky-wheel.component.html',
  styleUrls: ['./lucky-wheel.component.scss']
})
export class LuckyWheelComponent implements OnInit {

  activeBtn: boolean
  prizes: PrizeModel[]
  deg = 0
  prizeGroupId: number
  @ViewChild("inner") inner :ElementRef

  constructor(private luckyWheelService: LuckyWheelService,private renderer2: Renderer2) {
    this.luckyWheelService.findFirstByCurrentDateTime().subscribe({
      next: (model: PrizeGroup) => {
        this.prizes = model.prizeList
        this.prizeGroupId = model.id
      },
      error: (err: any) => {
        console.log("err-lucky-wheel", err)
      }
    })

  }

  ngOnInit(): void {

  }

  spin() {
    this.luckyWheelService.spin(this.prizeGroupId).subscribe({
      next: (result: LuckyWheelModel) => {

      },
      error: (err: any) => {
        console.log("spin-err", err)
      }
    })
    this.activeBtn = true
    setTimeout(() => (this.activeBtn = false), 5100)
    let spins = Math.floor(Math.random() * 7) + 9 //perform between 9 and 15 spins
    let wheelAngle = Math.floor(Math.random() * 12) * 30 //set wheel angle rotation
    let sectorAngle = Math.floor(Math.random() * 14) + 1 //set sector angle rotation
    sectorAngle *= Math.floor(Math.random() * 2) == 1 ? 1 : -1 //between -14deg and +14deg (28deg range of 30deg sector)
    this.deg += 360 * spins + wheelAngle + sectorAngle

    this.renderer2.setStyle( this.inner.nativeElement,"transform", `rotate(${this.deg}deg)`)
    setTimeout(() => (this.deg -= sectorAngle), 100) //reset sector angle rotation to avoid angle > +-44deg on next rotation

    let index = Math.floor((this.deg - sectorAngle) / 30) % 12 //get the prize

  }
}

