import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestsSolveComponent } from './tests-solve.component';

describe('TestsSolveComponent', () => {
    let component: TestsSolveComponent;
    let fixture: ComponentFixture<TestsSolveComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TestsSolveComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(TestsSolveComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});