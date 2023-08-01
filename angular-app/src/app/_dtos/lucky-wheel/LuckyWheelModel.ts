export class LuckyWheelModel {
  luckyNumber: number;
  prizeId: number;
  message: string;


  constructor(luckyNumber: number, prizeId: number, message: string) {
    this.luckyNumber = luckyNumber;
    this.prizeId = prizeId;
    this.message = message;
  }
}
