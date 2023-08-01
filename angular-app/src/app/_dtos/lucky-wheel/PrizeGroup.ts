import {PrizeModel} from "./PrizeModel";

export class PrizeGroup {
  id: number
  description: string
  dateTime: Date
  prizeList: PrizeModel[]
}
