/*
 * #%L
 * Alfresco Records Management Module
 * %%
 * Copyright (C) 2005 - 2017 Alfresco Software Limited
 * %%
 * This file is part of the Alfresco software.
 * -
 * If the software was purchased under a paid Alfresco license, the terms of
 * the paid license agreement will prevail.  Otherwise, the software is
 * provided under the following open source license terms:
 * -
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * -
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * -
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package org.alfresco.rest.rm.community.fileplancomponents;

import static org.alfresco.rest.rm.community.base.TestData.FOLDER_NAME;
import static org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponentAlias.FILE_PLAN_ALIAS;
import static org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponentAlias.HOLDS_ALIAS;
import static org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponentAlias.TRANSFERS_ALIAS;
import static org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponentAlias.UNFILED_RECORDS_CONTAINER_ALIAS;
import static org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponentType.CONTENT_TYPE;
import static org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponentType.NON_ELECTRONIC_RECORD_TYPE;
import static org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponentType.RECORD_FOLDER_TYPE;
import static org.alfresco.utility.data.RandomData.getRandomAlphanumeric;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

import org.alfresco.rest.rm.community.base.BaseRestTest;
import org.alfresco.rest.rm.community.base.TestData;
import org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponent;
import org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponentContent;
import org.alfresco.rest.rm.community.model.fileplancomponents.FilePlanComponentProperties;
import org.alfresco.rest.rm.community.requests.FilePlanComponentAPI;
import org.alfresco.rest.rm.community.requests.RecordsAPI;
import org.alfresco.utility.data.DataUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * This class contains the tests for
 * read records API
 *
 * @author Rodica Sutu
 * @since 2.6
 */
public class ReadRecordTests extends BaseRestTest
{

    @Autowired
    private FilePlanComponentAPI filePlanComponentAPI;
    @Autowired
    private RecordsAPI recordsAPI;

    @Autowired
    private DataUser dataUser;

    private String CATEGORY_NAME=TestData.CATEGORY_NAME +getRandomAlphanumeric();

    String ELECTRONIC_RECORD_NAME = "Record electronic" + getRandomAlphanumeric();
    String NONELECTRONIC_RECORD_NAME = "Record nonelectronic" + getRandomAlphanumeric();

    FilePlanComponent electronicRecord = FilePlanComponent.builder()
                                                          .name(ELECTRONIC_RECORD_NAME)
                                                          .nodeType(CONTENT_TYPE.toString())
                                                          .content(FilePlanComponentContent.builder().mimeType("text/plain").build())
                                                          .build();
    FilePlanComponent nonelectronicRecord = FilePlanComponent.builder()
                                                             .properties(FilePlanComponentProperties.builder()
                                                                                                    .description("Description")
                                                                                                    .title("Title")
                                                                                                    .build())
                                                             .name(NONELECTRONIC_RECORD_NAME)
                                                             .nodeType(NON_ELECTRONIC_RECORD_TYPE.toString())
                                                             .build();

    /**
     * Given a record category or a container which can't contain records
     * When I try to read the children filtering the results to records
     * Then I receive an empty list
     */
    @DataProvider(name="invalidContainersForRecords")
    public  Object[][] getContainers() throws Exception
    {
        return new Object[][] {
            { FILE_PLAN_ALIAS.toString() },
            { TRANSFERS_ALIAS.toString() },
            { HOLDS_ALIAS.toString() },
            { createCategory(FILE_PLAN_ALIAS.toString(), CATEGORY_NAME).getId()}
        };
    }
    @Test
    (
    dataProvider ="invalidContainersForRecords",
    description ="Reading records from invalid containers"
    )
    public void readRecordsFromInvalidContainers(String container) throws Exception
    {


        filePlanComponentAPI.usingRestWrapper().authenticateUser(dataUser.getAdminUser());

        FilePlanComponent electronicRecord = FilePlanComponent.builder()
                                                              .name(ELECTRONIC_RECORD_NAME)
                                                               .nodeType(CONTENT_TYPE.toString())
                                                               .content(FilePlanComponentContent.builder().mimeType("text/plain").build())
                                                               .build();
        FilePlanComponent nonelectronicRecord= FilePlanComponent.builder()
                                                                .properties(FilePlanComponentProperties.builder()
                                                                                                       .description("Description")
                                                                                                       .title("Title")
                                                                                                       .build())
                                                                .name(NONELECTRONIC_RECORD_NAME)
                                                                .nodeType(NON_ELECTRONIC_RECORD_TYPE.toString())
                                                                .build();
        //create records
        filePlanComponentAPI.createFilePlanComponent(electronicRecord,container);
        filePlanComponentAPI.createFilePlanComponent(nonelectronicRecord, container);

        // List children from API
        filePlanComponentAPI.withParams("where=(isFile=true)").listChildComponents(container)
                            //check the list returned is empty
                            .assertThat().entriesListIsEmpty().assertThat().paginationExist();
        // Check status code
        filePlanComponentAPI.usingRestWrapper().assertStatusCodeIs(OK);
    }
    //TODO MAYBE Update AC ??
    /**
     * Given a record
     * When I try to read the children
     * Then I receive error
     */
    @Test
    public void readChildrenOnRecordsString() throws Exception
    {
        String ELECTRONIC_RECORD_NAME = "Record electronic" + getRandomAlphanumeric();
        String NONELECTRONIC_RECORD_NAME = "Record nonelectronic" + getRandomAlphanumeric();
        String RELATIVE_PATH="CATEGORY"+ getRandomAlphanumeric()+"/FOLDER";

        filePlanComponentAPI.usingRestWrapper().authenticateUser(dataUser.getAdminUser());


        //create records in Unfiled Container
        FilePlanComponent recordElecInUnfiled = filePlanComponentAPI.createFilePlanComponent(electronicRecord, UNFILED_RECORDS_CONTAINER_ALIAS.toString());
        FilePlanComponent recordNonElecInUnfiled = filePlanComponentAPI.createFilePlanComponent(nonelectronicRecord, UNFILED_RECORDS_CONTAINER_ALIAS.toString());

        // List children for the electronic Record
        filePlanComponentAPI.withParams("where=(isFile=true)").listChildComponents(recordElecInUnfiled.getId())
                            //check the list returned is empty
                            .assertThat().entriesListIsEmpty().assertThat().paginationExist();
        // Check status code
        filePlanComponentAPI.usingRestWrapper().assertStatusCodeIs(OK);

        // List children for the nonElectronic Record
        filePlanComponentAPI.withParams("where=(isFile=true)").listChildComponents(recordNonElecInUnfiled.getId())
                            //check the list returned is empty
                            .assertThat().entriesListIsEmpty().assertThat().paginationExist();
        // Check status code
        filePlanComponentAPI.usingRestWrapper().assertStatusCodeIs(OK);

        //Update the Records objects
        electronicRecord.setRelativePath(RELATIVE_PATH);
        nonelectronicRecord.setRelativePath(RELATIVE_PATH);

        //create records in Unfiled Container
        FilePlanComponent recordElecFromRecordFolder = filePlanComponentAPI.createFilePlanComponent(electronicRecord, FILE_PLAN_ALIAS.toString());
        FilePlanComponent recordNonElecFromRecordFolder = filePlanComponentAPI.createFilePlanComponent(nonelectronicRecord, FILE_PLAN_ALIAS.toString());

        // List children for the electronic Record
        filePlanComponentAPI.withParams("where=(isFile=true)").listChildComponents(recordElecFromRecordFolder.getId())
                            //check the list returned is empty
                            .assertThat().entriesListIsEmpty().assertThat().paginationExist();
        // Check status code
        filePlanComponentAPI.usingRestWrapper().assertStatusCodeIs(OK);

        // List children for the nonElectronic Record
        filePlanComponentAPI.withParams("where=(isFile=true)").listChildComponents(recordNonElecFromRecordFolder.getId())
                            //check the list returned is empty
                            .assertThat().entriesListIsEmpty().assertThat().paginationExist();
        // Check status code
        filePlanComponentAPI.usingRestWrapper().assertStatusCodeIs(OK);
    }

    /**
     * Given a record
     * When I try to read the meta-data
     * Then I successfully receive the meta-data values for that record
     */
    @Test
    public void readRecordMetadata() throws Exception
    {
        recordsAPI.usingRestWrapper().authenticateUser(dataUser.getAdminUser());
        logger.info("NEW TEST" + recordsAPI.getRecordContentText("05978b97-bc84-4693-9f3f-f1b0502cfa7b").toString());

    }

    /**
     * Given an electronic record
     * When I try to read the content
     * Then I successfully receive the content of the record
     */
    @Test
    public void readRecordContent() throws Exception
    {
        String RECORD_ELECTRONIC= "Record " + getRandomAlphanumeric();
        String RELATIVE_PATH="/"+CATEGORY_NAME+ getRandomAlphanumeric()+"/folder";
        filePlanComponentAPI.usingRestWrapper().authenticateUser(dataUser.getAdminUser());
        //create the containers from the relativePath
        FilePlanComponent recordFolder = FilePlanComponent.builder()
                                                    .name(FOLDER_NAME)
                                                    .nodeType(RECORD_FOLDER_TYPE.toString())
                                                    .relativePath(RELATIVE_PATH)
                                                    .build();
        String folderId=filePlanComponentAPI.createFilePlanComponent(recordFolder,FILE_PLAN_ALIAS.toString()).getId();
        //
        FilePlanComponent record = FilePlanComponent.builder()
                                                    .name(RECORD_ELECTRONIC)
                                                    .nodeType(CONTENT_TYPE.toString())
                                                    .build();
        String recordId =filePlanComponentAPI.createElectronicRecord(record, createTempFile(RECORD_ELECTRONIC, RECORD_ELECTRONIC), folderId).getId();

        recordsAPI.usingRestWrapper().authenticateUser(dataUser.getAdminUser());

        assertEquals(recordsAPI.getRecordContentText(recordId),RECORD_ELECTRONIC);
        // Check status code
        recordsAPI.usingRestWrapper().assertStatusCodeIs(OK);

        FilePlanComponent recordNoContent = FilePlanComponent.builder()
                                                    .name(RECORD_ELECTRONIC)
                                                    .nodeType(CONTENT_TYPE.toString())
                                                    .build();
        String recordNoContentId=filePlanComponentAPI.createFilePlanComponent(recordNoContent,folderId).getId();
        assertTrue(recordsAPI.getRecordContentText(recordNoContentId).toString().isEmpty());
        recordsAPI.usingRestWrapper().assertStatusCodeIs(OK);
    }
    /**
     * Given a non-electronic record
     * When I try to read the content
     * Then I am informed that the record has no content
     */
    @Test
    public void readRecordNonElectronicContent() throws Exception
    {

        String NONELECTRONIC_RECORD_NAME = "Record nonelectronic" + getRandomAlphanumeric();
        FilePlanComponent record = FilePlanComponent.builder()
                                                    .name(NONELECTRONIC_RECORD_NAME)
                                                    .nodeType(NON_ELECTRONIC_RECORD_TYPE.toString())
                                                    .relativePath("/"+CATEGORY_NAME+"/"+FOLDER_NAME)
                                                    .build();
        filePlanComponentAPI.usingRestWrapper().authenticateUser(dataUser.getAdminUser());
        String nonElectronicRecord=filePlanComponentAPI.createFilePlanComponent(record,FILE_PLAN_ALIAS.toString()).getId();
        recordsAPI.usingRestWrapper().authenticateUser(dataUser.getAdminUser());
        assertTrue(recordsAPI.getRecordContentText(nonElectronicRecord).toString().isEmpty());
        recordsAPI.usingRestWrapper().assertStatusCodeIs(OK);
    }

    /**
     * Given a container (eg record folder, record category, etc)
     * When I try to read the content
     * Then I receive an error
     */
    @Test
    (
        dataProvider = "getContainers",
        dataProviderClass = TestData.class,
        description = "Reading records from invalid containers"
    )
    public void readContainFromInvalidContainers(String container) throws Exception
    {
        recordsAPI.usingRestWrapper().authenticateUser(dataUser.getAdminUser());
        recordsAPI.getRecordContentText(container).toString();
        recordsAPI.usingRestWrapper().assertStatusCodeIs(BAD_REQUEST);
    }

    /**
     * Given a container that is a record folder
     * When I try to record the containers records
     * Then I receive a list of all the records contained within the record folder
     */

    /**
     * Given a container this an unfiled record folder or the unfiled record container root
     * When I try to record the containers records
     * Then I receive a list of all the records contained within the unfiled record folder or the unfiled record container root
     */



}
