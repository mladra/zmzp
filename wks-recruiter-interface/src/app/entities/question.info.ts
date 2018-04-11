import { Account } from "./account";
import { Position } from "./position";
import { QuestionTypeEnum } from "./question.type.enum";

export class QuestionInfo {
    private questionNumber: Number;
    private questionPhrase: String;
    private type: QuestionTypeEnum;
    private params: any;
}