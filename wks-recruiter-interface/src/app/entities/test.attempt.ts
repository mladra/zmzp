import { AttemptAnswer } from './attempt.answer';
import { Test } from './test';

export class TestAttempt {
    id: string;
    user: string;
    test: Test;
    maxPoints: number;
    score: number;
    answers: AttemptAnswer[];
}
