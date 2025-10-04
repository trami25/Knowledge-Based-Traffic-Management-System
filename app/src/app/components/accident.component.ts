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
  selector: 'app-accident',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    DropdownModule,
    ButtonModule,
    CardModule,
  ],
  templateUrl: './accident.component.html',
  styleUrl: './accident.component.scss',
})
export class AccidentComponent {
  @Input() initialData?: any;
  @Output() formSubmit = new EventEmitter<any>();
  @Output() formReset = new EventEmitter<void>();

  form: FormGroup;

  severityOptions = [
    { label: 'Minor', value: 'minor' },
    { label: 'Major', value: 'major' },
    { label: 'Fatal', value: 'fatal' },
  ];

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      crossroad: ['', Validators.required],
      severity: ['', Validators.required],
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
