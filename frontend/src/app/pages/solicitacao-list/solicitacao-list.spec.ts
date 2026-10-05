import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SolicitacaoList } from './solicitacao-list';

describe('SolicitacaoList', () => {
  let component: SolicitacaoList;
  let fixture: ComponentFixture<SolicitacaoList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitacaoList],
    }).compileComponents();

    fixture = TestBed.createComponent(SolicitacaoList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
