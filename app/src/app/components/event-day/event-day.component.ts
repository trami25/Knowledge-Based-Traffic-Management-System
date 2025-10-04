import { Component, Input, Output, EventEmitter } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-event-day',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    InputNumberModule,
    DropdownModule,
    ButtonModule,
    CardModule,
  ],
  templateUrl: './event-day.component.html',
  styleUrl: './event-day.component.scss',
})
export class EventDayComponent {
  @Input() initialData?: any;
  @Output() formSubmit = new EventEmitter<any>();
  @Output() formReset = new EventEmitter<void>();

  form: FormGroup;

  eventTypeOptions = [
    { label: 'Concert', value: 'concert' },
    { label: 'Sports Event', value: 'sports event' },
    { label: 'Festival', value: 'festival' },
    { label: 'Conference', value: 'conference' },
    { label: 'Parade', value: 'parade' },
    { label: 'Market', value: 'market' },
  ];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      eventType: ['', Validators.required],
      expectedTraffic: [null, [Validators.required, Validators.min(0)]],
    });
  }

  ngOnInit() {
    if (this.initialData) {
      this.form.patchValue(this.initialData);
    }
  }

  onSubmit() {
    if (this.form.valid) {
      this.formSubmit.emit(this.form.value);
    }
  }

  onReset() {
    this.form.reset();
    this.formReset.emit();
  }

  get isValid(): boolean {
    return this.form.valid;
  }

  get formData(): any {
    return this.form.value;
  }
}
