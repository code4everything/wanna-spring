<!--suppress HtmlFormInputWithoutLabel, JSUndefinedPropertyAssignment, CssUnusedSymbol -->
<template>
  <div>
    <div v-for="(todo,index) in todos" :key="index" class="row todo" :data-index="index">
      <div class="col-sm-12 col-12" :id="idPrefix+index">
        <div class="pretty p-default p-round p-jelly">
          <input type="checkbox" @click="toggleStatus" :checked="todo.status==='1'"/>
          <div class="state p-success-o">
            <label></label>
          </div>
        </div>
        <input type="text" :class="['border-0 bg-light todo-item w-75',todo.status==='1'?'deleted':'']"
               v-model="todo.content" @blur="updateTodo" :disabled="todo.status==='1'"/>
        <a href="javascript:" class="text-danger" @click="remove" v-html="isMobile?removeIcon:removeTip"></a>
      </div>
    </div>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../../assets/js/utils'
import {requestRemoveTodo, requestToggleTodoStatus, requestUpdateTodo} from '../../../api/api'
import validator from '../../../../static/js/validator.min'
import layer from '../../../../static/js/layer'

export default {
  name: 'TodoItem',
  data () {
    return {
      removeIcon: '<i class="glyphicon glyphicon-trash"></i>',
      removeTip: '删除',
      isMobile: false
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
          layer.alert(data.msg)
        }
      })
    },
    remove: function () {
      let self = this
      let index = $(window.event.srcElement).parents('div.todo').attr('data-index')
      layer.confirm('是否确定删除索引位置位于 “' + (parseInt(index) + 1) + '” 的待办事项', {
        btn: ['确定', '取消']
      }, function () {
        layer.load(1)
        requestRemoveTodo(self.todos[index].id).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            self.todos.splice(index, 1)
          } else {
            layer.alert(data.msg)
          }
        })
        layer.closeAll()
      })
    },
    updateTodo: function () {
      let index = $(window.event.srcElement).parents('div.todo').attr('data-index')
      if (validator.isEmpty(this.todos[index].content)) {
        layer.alert('数据不能为空')
      } else {
        requestUpdateTodo(this.todos[index].id, this.todos[index].content).then(data => {
          if (data.code !== 200) {
            layer.alert(data.msg)
          }
        })
      }
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
