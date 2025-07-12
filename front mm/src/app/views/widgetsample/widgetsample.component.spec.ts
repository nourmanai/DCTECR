import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WidgetsampleComponent } from './widgetsample.component';

describe('WidgetsampleComponent', () => {
  let component: WidgetsampleComponent;
  let fixture: ComponentFixture<WidgetsampleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WidgetsampleComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(WidgetsampleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
