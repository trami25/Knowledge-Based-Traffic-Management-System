import { Component, Input, Output, EventEmitter } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { DropdownModule } from 'primeng/dropdown';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';

@Component({
  selector: 'app-emergency-vehicle',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    DropdownModule,
    ButtonModule,
    CardModule,
  ],
  templateUrl: './emergency-vehicle.component.html',
  styleUrl: './emergency-vehicle.component.scss',
})
export class EmergencyVehicleComponent {
  @Input() initialData?: any;
  @Output() formSubmit = new EventEmitter<any>();
  @Output() formReset = new EventEmitter<void>();

  form: FormGroup;

  directionOptions = [
    { label: 'North', value: 'north' },
    { label: 'South', value: 'south' },
    { label: 'East', value: 'east' },
    { label: 'West', value: 'west' },
  ];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      location: ['', Validators.required],
      direction: ['', Validators.required],
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
