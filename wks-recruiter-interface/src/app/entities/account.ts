import { TestAttempt } from "./test.attempt";

export class Account {
    id: string;
    login: string;
    name: string;
    surname: string;
    password: string;
    roles: string[];
    solvedTests: TestAttempt[];
}
