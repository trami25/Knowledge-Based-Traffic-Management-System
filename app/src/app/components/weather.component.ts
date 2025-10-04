import { Component, Input, Output, EventEmitter } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-weather',
  standalone: true,
  imports: [ReactiveFormsModule, DropdownModule, ButtonModule, CardModule],
  templateUrl: './weather.component.html',
  styleUrl: './weather.component.scss',
})
export class WeatherComponent {
  @Input() initialData?: any;
  @Output() formSubmit = new EventEmitter<any>();
  @Output() formReset = new EventEmitter<void>();

  form: FormGroup;

  typeOptions = [
    { label: 'Sunny', value: 'sunny' },
    { label: 'Rainy', value: 'rainy' },
    { label: 'Snowy', value: 'snowy' },
  ];

  intensityOptions = [
    { label: 'Light', value: 'light' },
    { label: 'Moderate', value: 'moderate' },
    { label: 'Heavy', value: 'heavy' },
  ];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      type: ['', Validators.required],
      intensity: ['', Validators.required],
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
