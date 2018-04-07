import { Account } from "./account";
import { Position } from "./position";

export class Test{
    id: string;
    author: Account;
    name: string;
    maxPoints: number;
    language: string;
    active: boolean;
    positions: Position[];
    //questions: QuestionInfo[];
}