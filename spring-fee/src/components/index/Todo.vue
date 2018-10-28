<!--suppress HtmlFormInputWithoutLabel -->
<template>
  <div class="row">
    <div class="text-left rounded bg-light col-10 offset-1 col-sm-11 offset-sm-1">
      <br/>
      <!--添加任务-->
      <div class="row">
        <div class="col-sm-12 col-12 border border-top-0 border-left-0 border-right-0 border-bottom"
             style="padding-bottom: 10px;margin-bottom: 10px;">
          <div class="pretty p-default p-round p-locked">
            <input type="checkbox"/>
            <div class="state p-success-o">
              <label></label>
            </div>
          </div>
          <input type="text" class="border-0 bg-light todo-item w-75" v-model="content" :placeholder="todoTip"
                 @keyup.enter="saveTodo"/>
        </div>
      </div>
      <!--任务列表-->
      <div v-for="(todo,index) in todos" :key="index" class="row todo" :data-index="index">
        <div class="col-sm-12 col-12" :id="idPrefix+index">
          <div class="pretty p-default p-round p-jelly">
            <input type="checkbox" @click="toggleStatus"/>
            <div class="state p-success-o">
              <label></label>
            </div>
          </div>
          <input type="text" :class="['border-0 bg-light todo-item w-75',todo.status==='1'?'deleted':'']"
                 v-model="todo.content" @blur="updateTodo"/>
          <a href="javascript:" class="text-danger" @click="remove" v-html="isMobile?removeIcon:removeTip"></a>
        </div>
      </div>
      <br/>
    </div>
  </div>
</template>

<script>/* eslint-disable */
import utils from '../../assets/js/utils'
import validator from '../../../static/js/validator.min'
import layer from '../../../static/js/layer'
import {
  requestListTodo,
  requestRemoveTodo,
  requestSaveTodo,
  requestToggleTodoStatus,
  requestUpdateTodo
} from '../../api/api'

export default {
  name: 'Todo',
  data () {
    return {
      removeIcon: '<i class="glyphicon glyphicon-trash"></i>',
      removeTip: '删除',
      todoTip: '添加任务',
      content: '',
      idPrefix: 'todo-item-',
      isMobile: false,
      defaultTodo: {content: '测试', createTime: '', doingDate: '', doneTime: '', id: '', status: '0'},
      todos: []
    }
  },
  props: ['date'],
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
          layer.alert(data.message)
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
            layer.alert(data.message)
          }
        })
        layer.closeAll()
      })
    },
    saveTodo: function () {
      if (validator.isEmpty(this.content)) {
        layer.alert('数据不能为空')
      } else {
        layer.load(1)
        requestSaveTodo(this.date, this.content).then(data => {
          layer.closeAll()
          if (data.code === 200) {
            this.content = ''
            this.todos.push(data.data)
          } else {
            layer.alert(data.message)
          }
        })
      }
    },
    updateTodo: function () {
      let index = $(window.event.srcElement).parents('div.todo').attr('data-index')
      if (validator.isEmpty(this.todos[index].content)) {
        layer.alert('数据不能为空')
      } else {
        requestUpdateTodo(this.todos[index].id, this.todos[index].content).then(data => {
          if (data.code !== 200) {
            layer.alert(data.message)
          }
        })
      }
    }
  },
  mounted: function () {
    this.isMobile = utils.isMobile()
  },
  watch: {
    date: function () {
      this.todos = []
      layer.load(1)
      requestListTodo(this.date).then(data => {
        layer.closeAll()
        if (data.code === 200) {
          this.todos = data.data
        } else {
          layer.alert(data.message)
        }
      })
    }
  },
  updated: function () {
    this.todos.forEach((todo, index) => {
      let parent = $(`#${this.idPrefix}${index}`)
      let status = $(parent).find('input[type="checkbox"]')
      let content = $(parent).find('input[type="text"]')
      if (todo.status === '1') {
        $(status).attr('checked', 'checked')
        $(content).attr('disabled', 'disabled')
      } else {
        $(status).removeAttr('checked')
        $(content).removeAttr('disabled')
      }
    })
  }
}
</script>

<!--suppress CssUnusedSymbol -->
<style scoped>
  @import '../../../static/css/pretty-checkbox.min.css';
  @import "../../assets/css/style.css";

  .deleted {
    text-decoration: line-through;
  }

  .todo-item {
    outline: none;
    font-size: 18px;
  }
</style>
