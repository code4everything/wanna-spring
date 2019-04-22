<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div aria-hidden="true" aria-labelledby="daily-modal-label" class="modal fade" id="daily-modal"
       role="dialog" tabindex="-1">
    <div :style="{width:isMobile?'90%':'100%'}" class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title" id="daily-modal-label">{{title}}</h4>
          <button aria-hidden="true" class="close" data-dismiss="modal"
                  type="button">&times;
          </button>
        </div>
        <div class="modal-body">
          <div :data-id="dailies.id" class="container data">
            <div class="row">
              <div class="col-sm-6 col-6">
                <el-time-picker :placeholder="startTimeTip" class="w-100" v-model="dailies.startTime"
                                value-format="HH:mm:ss"/>
              </div>
              <div class="col-sm-6 col-6">
                <el-time-picker :placeholder="endTimeTip" class="w-100" v-model="dailies.endTime"
                                value-format="HH:mm:ss"/>
              </div>
            </div>
            <br/>
            <div class="row">
              <div class="col-sm-12 col-12">
                <el-input :placeholder="contentTip" rows="3" type="textarea" v-model="dailies.content"></el-input>
              </div>
            </div>
          </div>
        </div>
        <div class="modal-footer">
          <el-button data-dismiss="modal" type="info"><i class="glyphicon glyphicon-remove"></i> {{closeTip}}
          </el-button>
          <el-button @click="remove" data-dismiss="modal" type="danger"><i class="glyphicon glyphicon-trash"></i>
            {{removeTip}}
          </el-button>
          <el-button @click="saveDailies" type="success"><i class="glyphicon glyphicon-floppy-open"></i> {{saveTip}}
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import {requestSaveDailies, requestUpdateDailies} from '../../api/api'
import validator from '../../../static/js/validator.min'

export default {
  name: 'DailyModal',
  data () {
    return {
      startTimeTip: '开始时间',
      endTimeTip: '结束时间',
      title: '编辑日程记录',
      isMobile: false,
      contentTip: '日程内容',
      closeTip: '关闭',
      saveTip: '保存',
      removeTip: '删除'
    }
  },
  props: ['dailies'],
  methods: {
    saveDailies: function () {
      if (!this.dailies.startTime || !this.dailies.endTime || validator.isEmpty(this.dailies.startTime) || validator.isEmpty(this.dailies.endTime) || validator.isEmpty(this.dailies.content)) {
        utils.showWarning(this, '数据不能为空')
      } else {
        if (validator.isEmpty(this.dailies.id)) {
          requestSaveDailies(this.$parent.daily.id, this.dailies).then(data => this.handleDailiesReturnData(data))
        } else {
          requestUpdateDailies(this.dailies.id, this.dailies).then(data => this.handleDailiesReturnData(data))
        }
      }
    },
    handleDailiesReturnData: function (data) {
      if (data.ok) {
        this.$parent.updateDailies(data.data)
      } else {
        utils.showError(this, data.msg)
      }
    },
    remove: function () {
      this.$parent.remove(this.dailies)
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
  }
}
</script>

<style scoped>

</style>
