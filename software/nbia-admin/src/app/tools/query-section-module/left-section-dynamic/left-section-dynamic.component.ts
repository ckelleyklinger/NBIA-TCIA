import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { DynamicQueryCriteriaService } from '@app/tools/query-section-module/dynamic-query-criteria/dynamic-query-criteria.service';

@Component( {
    selector: 'nbia-left-section-dynamic',
    templateUrl: './left-section-dynamic.component.html',
    styleUrls: ['./left-section-dynamic.component.scss']
} )
export class LeftSectionDynamicComponent implements OnInit, OnDestroy{
    usedElements = [];
    queryCriteriaData = [];

    testData0 = {
        'dynamicQueryCriteriaClearButton': true,
        'dynamicQueryCriteriaOpenCloseButton': true,
        'dynamicQueryCriteriaHeading': 'Test Widget',
        'dynamicQueryCriteriaSubHeading': 'Sub-heading',
        'dynamicQueryCriteriaApplyButton': true,
        'dynamicQueryCriteriaApplyText': 'Okay',
        'dynamicQueryCriteriaAllowNoChoice': true,
        'dynamicQueryCriteriaSelectionInHeadingCollapsed': true,
        'dynamicQueryCriteriaCalendar': true,
        'dynamicQueryCriteriaCalendarPrompt0': 'AAA',
        'dynamicQueryCriteriaCalendarPrompt1': 'BBB',
        'dynamicQueryCriteriaCalendarPlaceHolder0': 'CCC',
        'dynamicQueryCriteriaCalendarPlaceHolder1': 'DDD'
    };

    testData1 = {
        'dynamicQueryCriteriaSingleChoiceList': true,
        'dynamicQueryCriteriaClearButton': true,
        'dynamicQueryCriteriaOpenCloseButton': true,
        'dynamicQueryCriteriaHeading': 'Test Widget',
        'dynamicQueryCriteriaSubHeading': 'Sub-heading',
        'dynamicQueryCriteriaApplyButton': true,
        'dynamicQueryCriteriaApplyText': 'Okay',
        'dynamicQueryCriteriaSearchable': true,
        'dynamicQueryCriteriaSort': true,
        'dynamicQueryCriteriaListData': ['Zulu', 'Alpha', 'Bravo', 'Charlie', 'Delta', 'Echo', 'Fox', 'Golf', 'Hotel', 'India', 'Juliette', 'Kilo', 'Lima', 'Mike', 'November'],
        'dynamicQueryCriteriaAllowNoChoice': true,
        'dynamicQueryCriteriaSelectionInHeadingCollapsed': true,
        'dynamicQueryCriteriaCalendar': false,
        'dynamicQueryCriteriaCalendarPrompt0': 'AAA',
        'dynamicQueryCriteriaCalendarPrompt1': 'BBB',
        'dynamicQueryCriteriaCalendarPlaceHolder0': 'CCC',
        'dynamicQueryCriteriaCalendarPlaceHolder1': 'DDD'
    };

    testData2 = {

        'dynamicQueryCriteriaAllowNoChoice': true,
        'dynamicQueryCriteriaApplyButton': true,
        'dynamicQueryCriteriaApplyText': 'Okay',
        'dynamicQueryCriteriaCalendar': false,
        'dynamicQueryCriteriaCalendarPlaceHolder0': 'CCC',
        'dynamicQueryCriteriaCalendarPlaceHolder1': 'DDD',
        'dynamicQueryCriteriaCalendarPrompt0': 'AAA',
        'dynamicQueryCriteriaCalendarPrompt1': 'BBB',
        'dynamicQueryCriteriaClearButton': true,
        'dynamicQueryCriteriaHeading': 'Study UID',
        'dynamicQueryCriteriaOpenCloseButton': true,
        'dynamicQueryCriteriaSelectionInHeadingCollapsed': true,
        'dynamicQueryCriteriaSingleLineRadioDefault': 2,
        'dynamicQueryCriteriaSmallTextInput': true,
        'dynamicQueryCriteriaSort': true,
        'dynamicQueryCriteriaSubHeading': 'Enter Study UID'
    };

    private ngUnsubscribe: Subject<boolean> = new Subject<boolean>();

    constructor( private dynamicQueryCriteriaService: DynamicQueryCriteriaService ) {
    }

    ngOnInit() {
        this.dynamicQueryCriteriaService.initWidgetEmitter.pipe( takeUntil( this.ngUnsubscribe ) ).subscribe(
            async data => {
                this.addQueryCriteria( data );
            } );

/*
        this.dynamicQueryCriteriaService.initWidget( this.testData2 );
        this.dynamicQueryCriteriaService.initWidget( this.testData0 );
        this.dynamicQueryCriteriaService.initWidget( this.testData1 );
*/

    }

    addQueryCriteria( qCriteriaData ) {
        this.queryCriteriaData.reverse();
        this.queryCriteriaData.push( qCriteriaData );
        this.queryCriteriaData.reverse();
    }


    ngOnDestroy(): void {
        this.ngUnsubscribe.next();
        this.ngUnsubscribe.complete();
    }

}
