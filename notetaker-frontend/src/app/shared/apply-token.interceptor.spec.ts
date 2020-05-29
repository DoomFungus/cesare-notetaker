import { TestBed } from '@angular/core/testing';

import { ApplyTokenInterceptor } from './apply-token.interceptor';

describe('ApplyTokenInterceptor', () => {
  beforeEach(() => TestBed.configureTestingModule({
    providers: [
      ApplyTokenInterceptor
      ]
  }));

  it('should be created', () => {
    const interceptor: ApplyTokenInterceptor = TestBed.inject(ApplyTokenInterceptor);
    expect(interceptor).toBeTruthy();
  });
});
