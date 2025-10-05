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
  models = signal<any[]>([]);
  showHistoryLog = false;

  // Form and model configuration
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
  logs = computed((): any[] => this._logs() || []); // Explicit return type
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

  // Clear current results/logs
  clearCurrentResults() {
    this._logs.set([]);
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
          // Log successful response - REPLACE previous logs instead of adding
          const entry = {
            timestamp: new Date().toISOString(),
            models: instances.map((i) => i.type),
            payload: apiPayload,
            response: response,
            result: `Successfully processed ${instances.length} model instances`,
            status: 'success',
          };
          this._logs.set([entry]); // CLEAR previous and set only current result

          // Clear instances after successful submission
          this.clearAllInstances();
        },
        error: (error: any) => {
          // Log error response - REPLACE previous logs instead of adding
          const entry = {
            timestamp: new Date().toISOString(),
            models: instances.map((i) => i.type),
            payload: apiPayload,
            error: error.message || 'Unknown error',
            result: `Failed to process ${instances.length} model instances`,
            status: 'error',
          };
          this._logs.set([entry]); // CLEAR previous and set only current result
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
          // Log successful response - REPLACE previous logs instead of adding
          const entry = {
            timestamp: new Date().toISOString(),
            model: modelType,
            payload: formData,
            response: response,
            result: `Successfully processed ${modelType}`,
            status: 'success',
          };
          this._logs.set([entry]); // CLEAR previous and set only current result
        },
        error: (error: any) => {
          // Log error response - REPLACE previous logs instead of adding
          const entry = {
            timestamp: new Date().toISOString(),
            model: modelType,
            payload: formData,
            error: error.message || 'Unknown error',
            result: `Failed to process ${modelType}`,
            status: 'error',
          };
          this._logs.set([entry]); // CLEAR previous and set only current result
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

  // Helper methods for displaying output logs
  getModelListString(models: string[]): string {
    if (!models || models.length === 0) return 'No models';
    return models.join(', ');
  }

  getStatusClass(status: string): string {
    switch (status) {
      case 'success': return 'text-green-500';
      case 'error': return 'text-red-500';
      default: return 'text-gray-500';
    }
  }

  getTrafficActions(response: any[]): any[] {
    if (!response || !Array.isArray(response)) return [];
    return response.filter(item => item.query === 'TrafficAction' || item.queryName === 'TrafficAction');
  }

  getNotifications(response: any[]): any[] {
    if (!response || !Array.isArray(response)) return [];
    return response.filter(item => item.query === 'Notification' || item.queryName === 'Notification');
  }

  getValidationErrors(response: any[]): any[] {
    if (!response || !Array.isArray(response)) return [];
    return response.filter(item => item.query === 'ValidationError' || item.queryName === 'ValidationError');
  }

  getQueryResults(response: any[]): any[] {
    if (!response || !Array.isArray(response)) return [];
    return response.filter(item => 
      (item.query !== 'TrafficAction' && item.query !== 'Notification' && item.query !== 'ValidationError') &&
      (item.queryName !== 'TrafficAction' && item.queryName !== 'Notification' && item.queryName !== 'ValidationError')
    );
  }

  toggleRawResponse(logEntry: any): void {
    logEntry.showRaw = !logEntry.showRaw;
  }

  // Helper method to get current time for display
  getCurrentTime(): string {
    return new Date().toLocaleTimeString();
  }



  // Helper method to get notification priority
  getNotificationPriority(message: string): 'high' | 'medium' | 'low' {
    if (message.includes('accident') || message.includes('block') || message.includes('emergency')) {
      return 'high';
    }
    if (message.includes('weather') || message.includes('delay') || message.includes('density')) {
      return 'medium';
    }
    return 'low';
  }

  // Helper method to format action result with emoji
  formatActionResult(action: any): string {
    const actionText = action.cause || action.result || action.action || 'Unknown action';
    return `${actionText}`;
  }

  // Helper method to format notification message
  formatNotificationMessage(notification: any): string {
    return notification.cause || notification.message || notification.description || 'Unknown notification';
  }

  // Helper method to get crossroad info
  getCrossroadInfo(item: any): string {
    return item.crossroad || item.location || 'N/A';
  }

  // New methods for the updated UI
  toggleRequestDetails(logEntry: any): void {
    logEntry.showRequestDetails = !logEntry.showRequestDetails;
  }

  formatRequestData(logEntry: any): string {
    const requestData = {
      models: logEntry.models,
      timestamp: logEntry.timestamp,
      facts: logEntry.models?.map((m: any) => ({
        type: m.type,
        data: m.data
      })) || []
    };
    return JSON.stringify(requestData, null, 2);
  }

  hasAnyResponses(): boolean {
    return this.logs().some(log => 
      log.response && Array.isArray(log.response) && log.response.length > 0
    );
  }

  getAllActions(): any[] {
    const allActions: any[] = [];
    this.logs().forEach(log => {
      if (log.response) {
        allActions.push(...this.getTrafficActions(log.response));
      }
    });
    return allActions;
  }

  getAllNotifications(): any[] {
    const allNotifications: any[] = [];
    this.logs().forEach(log => {
      if (log.response) {
        allNotifications.push(...this.getNotifications(log.response));
      }
    });
    return allNotifications;
  }

  getAllValidationErrors(): any[] {
    const allErrors: any[] = [];
    this.logs().forEach(log => {
      if (log.response) {
        allErrors.push(...this.getValidationErrors(log.response));
      }
    });
    return allErrors;
  }

  getAllQueries(): any[] {
    const allQueries: any[] = [];
    this.logs().forEach(log => {
      if (log.response) {
        allQueries.push(...this.getQueryResults(log.response));
      }
    });
    return allQueries;
  }

  getTotalActions(): number {
    return this.getAllActions().length;
  }

  getTotalNotifications(): number {
    return this.getAllNotifications().length;
  }

  getTotalValidationErrors(): number {
    return this.getAllValidationErrors().length;
  }

  getTotalQueries(): number {
    return this.getAllQueries().length;
  }

  getNotificationIcon(message: string): string {
    if (message.includes('accident') || message.includes('block')) {
      return 'pi-exclamation-triangle';
    }
    if (message.includes('weather') || message.includes('alert')) {
      return 'pi-cloud';
    }
    if (message.includes('pedestrian')) {
      return 'pi-user';
    }
    if (message.includes('parking')) {
      return 'pi-car';
    }
    return 'pi-info-circle';
  }
}
