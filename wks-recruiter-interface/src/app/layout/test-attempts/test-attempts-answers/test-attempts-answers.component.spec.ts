import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { TestAttemptsAnswersComponent } from './test-attempts-answers.component';

describe('TestAttemptsAnswersComponent', () => {
    let component: TestAttemptsAnswersComponent;
    let fixture: ComponentFixture<TestAttemptsAnswersComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TestAttemptsAnswersComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(TestAttemptsAnswersComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
