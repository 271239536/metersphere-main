<template>
  <div>
    <ms-table
      v-loading="result"
      :show-select-all="false"
      :data="data"
      :operators="operators"
      :enable-selection="false"
      ref="table"
      :screen-height="null"
      @refresh="getTableData">
      <ms-table-column prop="targetNum" :label="$t('commons.id')" min-width="80" />

      <ms-table-column prop="targetName" :label="$t('commons.name')" min-width="120" />

      <ms-table-column v-xpack v-if="versionEnable" prop="versionName" :label="$t('project.version.name')">
        <template v-slot:default="scope">
          {{ versionOptions[scope.row.versionId] }}
        </template>
      </ms-table-column>

      <ms-table-column prop="creator" :label="$t('commons.create_user')" min-width="120"> </ms-table-column>

      <ms-table-column prop="status" min-width="120px" :label="$t('api_test.definition.api_status')">
        <template v-slot:default="scope">
          <span class="el-dropdown-link">
            <api-status :value="scope.row.status" />
          </span>
        </template>
      </ms-table-column>
    </ms-table>

    <api-relationship-relevance
      :api-definition-id="apiDefinitionId"
      @refresh="getTableData"
      :relationship-type="relationshipType"
      ref="testCaseRelevance" />
  </div>
</template>

<script>
import { getProjectVersions } from '@/api/xpack';
import MsTable from 'metersphere-frontend/src/components/table/MsTable';
import MsTableColumn from 'metersphere-frontend/src/components/table/MsTableColumn';
import MsTableSearchBar from 'metersphere-frontend/src/components/MsTableSearchBar';
import { getRelationshipApi } from '@/api/definition';
import ApiRelationshipRelevance from '@/business/definition/components/complete/ApiRelationshipRelevance';
import ApiStatus from '@/business/definition/components/list/ApiStatus';
import { getCurrentProjectID } from 'metersphere-frontend/src/utils/token';
import { hasLicense } from 'metersphere-frontend/src/utils/permission';

export default {
  name: 'ApiRelationshipList',
  components: {
    ApiStatus,
    ApiRelationshipRelevance,
    MsTableSearchBar,
    MsTableColumn,
    MsTable,
  },
  data() {
    return {
      result: false,
      data: [],
      operators: [
        {
          tip: this.$t('commons.delete'),
          icon: 'el-icon-delete',
          type: 'danger',
          exec: this.handleDelete,
          permissions: ['PROJECT_API_DEFINITION:READ+EDIT_API'],
          isDisable: this.readOnly,
        },
      ],
      condition: {},
      options: [],
      value: '',
      versionOptions: {},
    };
  },
  props: {
    apiDefinitionId: String,
    readOnly: Boolean,
    relationshipType: String,
    versionEnable: Boolean,
  },
  created() {
    this.getProjectVersions();
  },
  methods: {
    getTableData() {
      getRelationshipApi(this.apiDefinitionId, this.relationshipType).then((response) => {
        this.data = response.data;
        this.$emit('setCount', response.data.length);
      });
    },
    openRelevance() {
      this.$refs.testCaseRelevance.open();
    },
    handleDelete(item) {
      this.$emit('deleteRelationship', item.sourceId, item.targetId);
    },
    getProjectVersions() {
      if (hasLicense()) {
        getProjectVersions(getCurrentProjectID()).then((response) => {
          this.versionOptions = response.data.reduce((result, next) => {
            result[next.id] = next.name;
            return result;
          }, {});
        });
      }
    },
  },
};
</script>

<style scoped></style>
