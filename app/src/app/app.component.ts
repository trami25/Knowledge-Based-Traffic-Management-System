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
  TitleCasePipe,
} from '@angular/common';

// Import new dynamic components
import { AccidentComponent } from './components/accident/accident.component';
import { VehicleComponent } from './components/vehicle/vehicle.component';
import { WeatherComponent } from './components/weather/weather.component';
import { TrafficDensityComponent } from './components/traffic-density/traffic-density.component';
import { EmergencyVehicleComponent } from './components/emergency-vehicle/emergency-vehicle.component';
import { CauseFactComponent } from './components/cause-fact/cause-fact.component';
import { TimeOfDayComponent } from './components/time-of-day/time-of-day.component';
import { CrossroadComponent } from './components/crossroad/crossroad.component';
import { EventDayComponent } from './components/event-day/event-day.component';
import { IllegalParkingComponent } from './components/illegal-parking/illegal-parking.component';
import { PedestrianDetectedComponent } from './components/pedestrian-detected/pedestrian-detected.component';
import { PublicTransportDelayComponent } from './components/public-transport-delay/public-transport-delay.component';

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
    TitleCasePipe,
    // Dynamic form components
    AccidentComponent,
    VehicleComponent,
    WeatherComponent,
    TrafficDensityComponent,
    EmergencyVehicleComponent,
    CauseFactComponent,
    TimeOfDayComponent,
    CrossroadComponent,
    EventDayComponent,
    IllegalParkingComponent,
    PedestrianDetectedComponent,
    PublicTransportDelayComponent,
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
    { label: 'Crossroad', value: 'crossroad' },
    { label: 'Event Day', value: 'eventDay' },
    { label: 'Illegal Parking', value: 'illegalParking' },
    { label: 'Pedestrian Detected', value: 'pedestrianDetected' },
    { label: 'Public Transport Delay', value: 'publicTransportDelay' },
  ];

  form: FormGroup;
  private _logs = signal<any[]>([]);
  logs = computed(() => this._logs());
  darkModeToggle = false;

  // Multiple models support
  private _modelInstances = signal<
    Array<{ id: string; type: string; data: any }>
  >([]);
  modelInstances = computed(() => this._modelInstances());
  private instanceCounter = 0;

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
    this.addModelInstance(model, formData);
  }

  addModelInstance(modelType: string, formData: any) {
    const id = `instance_${++this.instanceCounter}`;
    const newInstance = {
      id,
      type: modelType,
      data: formData,
    };
    this._modelInstances.update((instances) => [...instances, newInstance]);

    // Reset the form for next entry
    this.form.reset();
  }

  removeModelInstance(instanceId: string) {
    this._modelInstances.update((instances) =>
      instances.filter((instance) => instance.id !== instanceId)
    );
  }

  clearAllInstances() {
    this._modelInstances.set([]);
  }

  submitAllInstances() {
    const instances = this._modelInstances();
    if (instances.length === 0) {
      alert('Please add at least one model instance before submitting.');
      return;
    }

    // Format all instances for API
    const apiPayload = {
      facts: instances.map((instance) => ({
        type: this.getJavaClassName(instance.type),
        payload: instance.data,
      })),
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
            models: instances.map((i) => i.type),
            payload: apiPayload,
            response: response,
            result: `Successfully processed ${instances.length} model instances`,
            status: 'success',
          };
          this._logs.update((arr) => [entry, ...arr]);

          // Clear instances after successful submission
          this.clearAllInstances();
        },
        error: (error: any) => {
          // Log error response
          const entry = {
            timestamp: new Date().toISOString(),
            models: instances.map((i) => i.type),
            payload: apiPayload,
            error: error.message || 'Unknown error',
            result: `Failed to process ${instances.length} model instances`,
            status: 'error',
          };
          this._logs.update((arr) => [entry, ...arr]);
          console.error('API Error:', error);
        },
      });
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
