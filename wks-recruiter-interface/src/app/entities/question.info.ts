import { Account } from './account';
import { Position } from './position';
import { QuestionTypeEnum } from './question.type.enum';

export class QuestionInfo {
    questionNumber: Number;
    questionPhrase: String;
    type: string;
    params: any;

    constructor() {
        this.type = 'NONE';
    }
}
