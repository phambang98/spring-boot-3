export class LuckyWheelModel {
  luckyNumber: number
  spinNumber :number
  prizeId: number
  message: string


  constructor(luckyNumber: number, numberSpin: number, prizeId: number, message: string) {
    this.luckyNumber = luckyNumber;
    this.spinNumber = numberSpin;
    this.prizeId = prizeId;
    this.message = message;
  }
}
