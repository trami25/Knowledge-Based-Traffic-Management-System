import { Component, Input, Output, EventEmitter } from '@angular/core';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
  FormArray,
} from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-crossroad',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    InputTextModule,
    ButtonModule,
    CardModule,
    NgFor,
  ],
  templateUrl: './crossroad.component.html',
  styleUrl: './crossroad.component.scss',
})
export class CrossroadComponent {
  @Input() initialData?: any;
  @Output() formSubmit = new EventEmitter<any>();
  @Output() formReset = new EventEmitter<void>();

  form: FormGroup;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      id: ['', Validators.required],
      connectedRoads: this.fb.array([this.fb.control('', Validators.required)]),
    });
  }

  ngOnInit() {
    if (this.initialData) {
      this.form.patchValue(this.initialData);
    }
  }

  get connectedRoads() {
    return this.form.get('connectedRoads') as FormArray;
  }

  addRoad() {
    this.connectedRoads.push(this.fb.control('', Validators.required));
  }

  removeRoad(index: number) {
    if (this.connectedRoads.length > 1) {
      this.connectedRoads.removeAt(index);
    }
  }

  onSubmit() {
    if (this.form.valid) {
      const formData = {
        ...this.form.value,
        connectedRoads: this.form.value.connectedRoads.filter(
          (road: string) => road.trim() !== ''
        ),
      };
      this.formSubmit.emit(formData);
    }
  }

  onReset() {
    this.form.reset();
    // Reset to one empty road
    while (this.connectedRoads.length > 1) {
      this.connectedRoads.removeAt(1);
    }
    this.formReset.emit();
  }

  get isValid(): boolean {
    return this.form.valid;
  }

  get formData(): any {
    return this.form.value;
  }
}
