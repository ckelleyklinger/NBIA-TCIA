import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LeftSectionComponent } from './left-section/left-section.component';
import { QueryBatchComponent } from './query-batch/query-batch.component';
import { QueryCollectionComponent } from './query-collection/query-collection.component';
import { QueryConfirmedComponent } from './query-confirmed/query-confirmed.component';
import { QueryMostRecentSubmissionDateComponent } from './query-most-recent-submission-date/query-most-recent-submission-date.component';
import { QueryPatientIdComponent } from './query-patient-id/query-patient-id.component';
import { QueryQcStatusComponent } from './query-qc-status/query-qc-status.component';
import { QueryReleasedComponent } from './query-released/query-released.component';
import { QuerySectionComponent } from './query-section/query-section.component';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from '@app/app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { HeaderModule } from '@app/header-module/header.module';
import { ViewSubmissionReportsModule } from '../view-submission-reports-module/view-submission-reports.module';
import { PerformOnlineDeletionModule } from '../perform-online-deletion-module/perform-online-deletion.module';
import { DynamicQueryCriteriaComponent } from './dynamic-query-criteria/dynamic-query-criteria.component';
import { WidgetComponent } from './dynamic-query-criteria/widget/widget.component';
import { WidgetTestSettingsComponent } from './dynamic-query-criteria/widget/widget-test-settings/widget-test-settings.component';
import { WidgetCalendarComponent } from './dynamic-query-criteria/widget/widget-calendar/widget-calendar.component';
import { LeftSectionDynamicComponent } from './left-section-dynamic/left-section-dynamic.component';
import { AdminCommonPipeModule } from '@app/admin-common/admin-common-pipe-module/admin-common-pipe.module';
import { WidgetCalendarBravoComponent } from './dynamic-query-criteria/widget/widget-calendar-bravo/widget-calendar-bravo.component';



@NgModule( {
    declarations: [
        LeftSectionComponent,
        QueryBatchComponent,
        QueryCollectionComponent,
        QueryConfirmedComponent,
        QueryMostRecentSubmissionDateComponent,
        QueryPatientIdComponent,
        QueryQcStatusComponent,
        QueryReleasedComponent,
        QuerySectionComponent,
        DynamicQueryCriteriaComponent,
        WidgetComponent,
        WidgetTestSettingsComponent,
        WidgetCalendarComponent,
        LeftSectionDynamicComponent,
        WidgetCalendarBravoComponent
    ],
    exports: [
        LeftSectionComponent,
        QueryBatchComponent,
        QueryCollectionComponent,
        QueryConfirmedComponent,
        QueryMostRecentSubmissionDateComponent,
        QueryPatientIdComponent,
        QueryQcStatusComponent,
        QueryReleasedComponent,
        QuerySectionComponent,
        DynamicQueryCriteriaComponent,
        WidgetTestSettingsComponent,
        WidgetComponent,
        LeftSectionDynamicComponent
    ],
    imports: [
        CommonModule,
        BrowserModule,
        FormsModule,
        AppRoutingModule,
        HttpClientModule,
        HeaderModule,
        ViewSubmissionReportsModule,
        PerformOnlineDeletionModule,
        AdminCommonPipeModule
    ]
} )
export class QuerySectionModule{
}
