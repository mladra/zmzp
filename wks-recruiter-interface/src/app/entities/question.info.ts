export class QuestionInfo {
    questionNumber: Number;
    questionPhrase: String;
    maxPoints: Number;
    type: string;
    params: any;

    constructor() {
        this.type = 'NONE';
    }
}
