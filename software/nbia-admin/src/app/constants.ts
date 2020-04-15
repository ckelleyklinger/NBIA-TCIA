export enum ToolItems{
    DATA_ADMIN_PERFORM_QC_MENU_ITEM,
    DATA_ADMIN_APPROVE_DELETIONS_MENU_ITEM,
    DATA_ADMIN_VIEW_SUBMISSION_REPORTS_MENU_ITEM,
    DATA_ADMIN_PERFORM_ONLINE_DELETION_MENU_ITEM,
    DATA_ADMIN_EDIT_COLLECTION_DESCRIPTIONS_MENU_ITEM,
    DATA_ADMIN_MANAGE_WORKFLOW_ITEMS_MENU_ITEM
}
export enum TokenStatus{
    NO_TOKEN,
    NO_TOKEN_YET,
    EXP_TOKEN,
    BAD_TOKEN,
    HAVE_TOKEN,
    GOOD_TOKEN
}

export const Consts = {
    URL_KEY_TOOL: 'tool',
    URL_KEY_TOKEN: 'accessToken',

    TOOL_PERFORM_QC: 'perform-qc',
    TOOL_BULK_QC: 'bulk-qc',
    TOOL_APPROVE_DELETIONS: 'approve-deletions',
    TOOL_EDIT_COLLECTION_DESCRIPTIONS: 'edit-collection-descriptions',
    TOOL_PERFORM_ONLINE_DELETION: 'perform-online-deletion',
    TOOL_VIEW_SUBMISSION_REPORTS: 'view-submission-reports',
    TOOL_NONE: 'no-tool',

    // For Query section
    QUERY_CRITERIA_QC_STATUS: 'queryCriteriaQcStatus',
    QUERY_CRITERIA_COLLECTION: 'queryCriteriaQcCollection',
    QUERY_CRITERIA_RELEASED: 'queryCriteriaReleased',
    QUERY_CRITERIA_BATCH_NUMBER: 'queryCriteriaBatchNumber',
    QUERY_CRITERIA_CONFIRMED_COMPLETE: 'queryCriteriaConfirmedComplete',
    QUERY_CRITERIA_SUBJECT_ID: 'queryCriteriaSubjectId',
    QUERY_CRITERIA_MOST_RECENT_SUBMISSION: 'queryCriteriaMostRecentSubmission',
    SUBMIT_QC_STATUS_UPDATE: 'submitQCVisibilityForDataAdmin',

    CRITERIA_SEARCH: 0,
    TEXT_SEARCH: 1,

    ACCESS_TOKEN: '',

    GET_USER_ROLES: 'getRoles',
    GET_COLLECTION_NAMES: 'getCollectionValuesAndCounts',
    GET_COLLECTION_NAMES_AND_SITES: 'getCollectionSite',
    GET_COLLECTION_DESCRIPTIONS: 'getCollectionDescriptions',
    GET_SEARCH_FOR_APPROVE_DELETIONS: 'getQCSearchForDeletion',
    GET_HISTORY_REPORT: 'getQCHistoryReport',
    GET_VISIBILITIES: 'getVisibilities',
    GET_HISTORY_REPORT_TABLE: 'getQCHistoryReportTable',
    GET_SEARCH_FOR_PERFORM_QC: 'getQCSearch',
    UPDATE_COLLECTION_DESCRIPTION: 'submitCollectionDescription',

    DICOM_TAGS: 'getDicomTags',
    DICOM_TAGS_BY_IMAGE: 'getDicomTagsByImageID',

    ERROR_401: 'Unauthorized',
    ERROR_NO_ACCESS_TOKEN: 'No Access token',
    GOOD_TOKEN: 'Good Token',
    NEED_LOGIN: 'Need Login',
    ERROR: 'Error',

    waitTime: 11,

    API_ACCESS_TOKEN_URL: 'nbia-api/oauth/token',
    API_CLIENT_SECRET_DEFAULT: 'ItsBetweenUAndMe',

    QC_STATUSES: ['Not Yet Reviewed', 'Visible', 'Not Visible',
        'To Be Deleted', 'First Review', 'Second Review', 'Third Review',
        'Fourth Review', 'Fifth Review', 'Sixth Review', 'Seventh Review', 'Downloadable'],

    NO_SEARCH: 'NO_SEARCH'

};


export enum SortState{
    NO_SORT,
    SORT_UP,
    SORT_DOWN
}
