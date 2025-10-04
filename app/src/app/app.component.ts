import { Component, computed, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import {
  ReactiveFormsModule,
  FormsModule,
  FormBuilder,
  Validators,
  FormGroup,
} from '@angular/forms';
import { DropdownModule } from 'primeng/dropdown';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { DividerModule } from 'primeng/divider';
import {
  JsonPipe,
  NgFor,
  NgIf,
  NgSwitch,
  NgSwitchCase,
  NgSwitchDefault,
} from '@angular/common';

// Import new dynamic components
import { AccidentComponent } from './components/accident.component';
import { VehicleComponent } from './components/vehicle.component';
import { WeatherComponent } from './components/weather.component';
import { TrafficDensityComponent } from './components/traffic-density.component';
import { EmergencyVehicleComponent } from './components/emergency-vehicle.component';
import { CauseFactComponent } from './components/cause-fact.component';
import { TimeOfDayComponent } from './components/time-of-day.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [
    RouterOutlet,
    ReactiveFormsModule,
    FormsModule,
    DropdownModule,
    ToggleButtonModule,
    ButtonModule,
    CardModule,
    DividerModule,
    NgFor,
    NgIf,
    NgSwitch,
    NgSwitchCase,
    NgSwitchDefault,
    JsonPipe,
    // Dynamic form components
    AccidentComponent,
    VehicleComponent,
    WeatherComponent,
    TrafficDensityComponent,
    EmergencyVehicleComponent,
    CauseFactComponent,
    TimeOfDayComponent,
  ],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  modelOptions = [
    { label: 'Accident', value: 'accident' },
    { label: 'Vehicle', value: 'vehicle' },
    { label: 'Weather', value: 'weather' },
    { label: 'Traffic Density', value: 'trafficDensity' },
    { label: 'Emergency Vehicle', value: 'emergencyVehicle' },
    { label: 'Cause Fact', value: 'causeFact' },
    { label: 'Time of Day', value: 'timeOfDay' },
  ];

  form: FormGroup;
  private _logs = signal<any[]>([]);
  logs = computed(() => this._logs());
  darkModeToggle = false;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.form = this.fb.group({
      model: [null, Validators.required],
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

  onDynamicFormSubmit(formData: any) {
    const model = this.form.value.model;
    this.onSubmit(model, formData);
  }

  onSubmit(modelType: string, formData: any) {
    // Format the data according to the API specification
    const apiPayload = {
      facts: [
        {
          type: this.getJavaClassName(modelType),
          payload: formData,
        },
      ],
      queries: [],
    };

    // Make HTTP POST request to the backend
    this.http
      .post('http://localhost:8080/api/rules/execute', apiPayload, {
        headers: {
          'Content-Type': 'application/json',
        },
      })
      .subscribe({
        next: (response: any) => {
          // Log successful response
          const entry = {
            timestamp: new Date().toISOString(),
            model: modelType,
            payload: formData,
            response: response,
            result: `Successfully processed ${modelType}`,
            status: 'success',
          };
          this._logs.update((arr) => [entry, ...arr]);
        },
        error: (error: any) => {
          // Log error response
          const entry = {
            timestamp: new Date().toISOString(),
            model: modelType,
            payload: formData,
            error: error.message || 'Unknown error',
            result: `Failed to process ${modelType}`,
            status: 'error',
          };
          this._logs.update((arr) => [entry, ...arr]);
          console.error('API Error:', error);
        },
      });
  }

  private getJavaClassName(modelType: string): string {
    // Map Angular component names to Java class names
    const classNameMap: { [key: string]: string } = {
      accident: 'Accident',
      vehicle: 'Vehicle',
      weather: 'Weather',
      trafficDensity: 'TrafficDensity',
      emergencyVehicle: 'EmergencyVehicle',
      causeFact: 'CauseFact',
      timeOfDay: 'TimeOfDay',
      crossroad: 'Crossroad',
      eventDay: 'EventDay',
      illegalParking: 'IllegalParking',
      notification: 'Notification',
      pedestrianDetected: 'PedestrianDetected',
      publicTransportDelay: 'PublicTransportDelay',
      trafficAction: 'TrafficAction',
    };

    return classNameMap[modelType] || modelType;
  }

  onDynamicFormReset() {
    // Reset the main form model selection
    this.form.reset();
  }

  darkMode(): boolean {
    return document.body.classList.contains('dark');
  }

  toggleDarkMode() {
    document.body.classList.toggle('dark', this.darkModeToggle);
    localStorage.setItem('darkMode', String(this.darkModeToggle));
  }
}
