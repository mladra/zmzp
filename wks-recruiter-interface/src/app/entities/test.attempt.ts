import { AttemptAnswer } from "./attempt.answer";
import { Test } from "./test";

export class TestAttempt {
    test: Test;
    points: number;
    answers: AttemptAnswer[];
}