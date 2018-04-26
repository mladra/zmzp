export class QuestionInfo {
    questionNumber: Number;
    questionPhrase: String;
    maxPoints: Number;
    type: string;
    params: any;
    answer: any;

    constructor() {
        this.type = 'NONE';
    }
}
