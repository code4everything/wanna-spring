<!--suppress HtmlFormInputWithoutLabel, JSUndefinedPropertyAssignment, CssUnusedSymbol -->
<template>
  <div>
    <div :data-index="index" :key="index" class="row todo" v-for="(todo,index) in todos">
      <div :id="idPrefix+index" class="col-sm-12 col-12">
        <div class="pretty p-default p-round p-jelly">
          <input :checked="todo.status==='1'" @click="toggleStatus" type="checkbox"/>
          <div class="state p-success-o">
            <label></label>
          </div>
        </div>
        <el-tooltip effect="dark" :content="todo.doingDate" placement="top-start" :enterable="false"
                    :disabled="todo.doingDate===$parent.date">
          <input :class="['border-0 bg-light todo-item w-75',todo.status==='1'?'deleted':'']"
                 :disabled="todo.status==='1'" @focus="watchTodoContent" @blur="updateTodo(null)" type="text"
                 v-model="todo.content" @keyup.enter="updateTodo(null)"/>
        </el-tooltip>
        <a @click="remove" class="text-danger" href="javascript:" v-html="isMobile?removeIcon:removeTip"></a>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../../assets/js/utils'
import {requestRemoveTodo, requestToggleTodoStatus, requestUpdateTodo} from '../../../api/api'
import validator from '../../../../static/js/validator.min'

export default {
  name: 'TodoItem',
  data () {
    return {
      removeIcon: '<i class="glyphicon glyphicon-trash"></i>',
      removeTip: '删除',
      isMobile: false,
      currentTodoContent: '',
      timeoutCount: 0
    }
  },
  props: ['todos', 'idPrefix'],
  methods: {
    toggleStatus: function () {
      let src = window.event.srcElement
      let index = $(src).parents('div.todo').attr('data-index')
      let status = src.checked ? '1' : '0'
      requestToggleTodoStatus(this.todos[index].id, status).then(data => {
        if (data.code === 200) {
          this.todos[index].status = status
        } else {
          src.checked = false
          utils.showError(this, data.msg)
        }
      })
    },
    remove: function () {
      let self = this
      let index = $(window.event.srcElement).parents('div.todo').attr('data-index')
      this.$confirm(`是否确定删除索引位置位于 '${parseInt(index) + 1}' 的待办事项`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        requestRemoveTodo(self.todos[index].id).then(data => {
          if (data.code === 200) {
            self.todos.splice(index, 1)
            utils.showSuccess(this, data.msg)
          } else {
            utils.showError(this, data.msg)
          }
        })
      }).catch(() => {
      })
    },
    updateTodo: function (srcEle) {
      let index = $(srcEle || window.event.srcElement).parents('div.todo').attr('data-index')
      let content = this.todos[index].content
      if (validator.isEmpty(content)) {
        utils.showWarning(this, '数据不能为空')
      } else if (this.currentTodoContent !== content) {
        // 当值发生变化时，更新内容
        requestUpdateTodo(this.todos[index].id, content)
        this.currentTodoContent = content
      }
    },
    watchTodoContent: function () {
      const srcEle = window.event.srcElement
      this.currentTodoContent = $(srcEle).val()
      // 防止切换频繁时导致频繁请求更新
      const current = ++this.timeoutCount
      setTimeout(() => {
        if (current === this.timeoutCount) {
          this.updateTodo(srcEle)
        }
      }, 10 * 1000)
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
  }
}
</script>

<style scoped>
  @import '../../../../static/css/pretty-checkbox.min.css';
  @import "../../../assets/css/style.css";

  .deleted {
    text-decoration: line-through;
  }

  .todo-item {
    outline: none;
    font-size: 18px;
  }
</style>
