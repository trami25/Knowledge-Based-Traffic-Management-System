import { Component, computed, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {
  ReactiveFormsModule,
  FormsModule,
  FormBuilder,
  Validators,
  FormGroup,
} from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { CalendarModule } from 'primeng/calendar';
import { TextareaModule } from 'primeng/textarea';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';
import { PanelModule } from 'primeng/panel';
import { TagModule } from 'primeng/tag';
import { ChipModule } from 'primeng/chip';
import {
  JsonPipe,
  NgFor,
  NgIf,
  NgSwitch,
  NgSwitchCase,
  NgSwitchDefault,
} from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    ReactiveFormsModule,
    FormsModule,
    DropdownModule,
    InputTextModule,
    InputNumberModule,
    CalendarModule,
    TextareaModule,
    ToggleButtonModule,
    ButtonModule,
    CardModule,
    DividerModule,
    PanelModule,
    TagModule,
    ChipModule,
    NgFor,
    NgIf,
    NgSwitch,
    NgSwitchCase,
    NgSwitchDefault,
    JsonPipe,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  modelOptions = [
    { label: 'Traffic Flow', value: 'trafficFlow' },
    { label: 'Weather Impact', value: 'weatherImpact' },
    { label: 'Incident Prediction', value: 'incidentPrediction' },
  ];

  form: FormGroup;
  private _logs = signal<any[]>([]);
  logs = computed(() => this._logs());
  darkModeToggle = false;

  constructor(private fb: FormBuilder) {
    this.form = this.fb.group({
      model: [null, Validators.required],
      // trafficFlow
      roadSegment: [''],
      vehiclesPerHour: [null],
      avgSpeed: [null],
      // weatherImpact
      dateTime: [null],
      conditions: [''],
      temperature: [null],
      // incidentPrediction
      description: [''],
      numCameras: [null],
      numSensors: [null],
    });

    // Update validators when model changes
    this.form.get('model')!.valueChanges.subscribe((model: string) => {
      this.applyModelValidators(model);
    });

    // Initialize dark mode from persisted preference
    const persisted = localStorage.getItem('darkMode');
    if (persisted !== null) {
      const isDark = persisted === 'true';
      this.darkModeToggle = isDark;
      // Apply dark mode immediately
      if (isDark) {
        document.body.classList.add('dark');
      } else {
        document.body.classList.remove('dark');
      }
    }
  }

  private applyModelValidators(model: string) {
    const setReq = (name: string, required: boolean) => {
      const ctrl = this.form.get(name);
      if (!ctrl) return;
      ctrl.setValidators(required ? [Validators.required] : []);
      ctrl.updateValueAndValidity({ emitEvent: false });
    };

    // reset all to optional
    [
      'roadSegment',
      'vehiclesPerHour',
      'avgSpeed',
      'dateTime',
      'conditions',
      'temperature',
      'description',
      'numCameras',
      'numSensors',
    ].forEach((n) => setReq(n, false));

    switch (model) {
      case 'trafficFlow':
        setReq('roadSegment', true);
        setReq('vehiclesPerHour', true);
        setReq('avgSpeed', true);
        break;
      case 'weatherImpact':
        setReq('dateTime', true);
        setReq('conditions', true);
        setReq('temperature', true);
        break;
      case 'incidentPrediction':
        setReq('description', true);
        setReq('numCameras', true);
        setReq('numSensors', true);
        break;
    }
  }

  onSubmit() {
    if (this.form.invalid) return;
    const model = this.form.value.model;
    const payload = this.buildPayload(model);
    // Simulate API call
    const entry = {
      timestamp: new Date().toISOString(),
      model,
      payload,
      result: `Processed ${model} successfully`,
    };
    this._logs.update((arr) => [entry, ...arr]);
  }

  reset() {
    this.form.reset();
  }

  private buildPayload(model: string) {
    const v = this.form.value as any;
    switch (model) {
      case 'trafficFlow':
        return {
          roadSegment: v.roadSegment,
          vehiclesPerHour: v.vehiclesPerHour,
          avgSpeed: v.avgSpeed,
        };
      case 'weatherImpact':
        return {
          dateTime: v.dateTime,
          conditions: v.conditions,
          temperature: v.temperature,
        };
      case 'incidentPrediction':
        return {
          description: v.description,
          numCameras: v.numCameras,
          numSensors: v.numSensors,
        };
      default:
        return {};
    }
  }

  darkMode(): boolean {
    return document.body.classList.contains('dark');
  }

  toggleDarkMode() {
    document.body.classList.toggle('dark', this.darkModeToggle);
    localStorage.setItem('darkMode', String(this.darkModeToggle));
  }
}
