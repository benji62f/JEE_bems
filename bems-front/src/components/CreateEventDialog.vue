<script>
import axios from "axios";
import moment from "moment-timezone";

export default {
  data: () => ({
    dialog: false,
    label: "",
    description: "",
    startDate: "",
    endDate: "",
    eventId: "",
    eventColor: "grey darken-1",
    errorOnDateValues: true,
    eventLabelMaxLength: 32,
    eventDescriptionMaxLength: 150,
    colors: [
      "blue",
      "indigo",
      "deep-purple",
      "cyan",
      "green",
      "orange",
      "grey darken-1",
    ],
    errorMessage: "",
  }),
  methods: {
    clearFields() {
      this.label = "";
      this.description = "";
      this.startDate = this.endDate = moment(new Date()).format("YYYY-MM-DDTHH:mm");
      this.errorOnDateValues = true;
      const colorBtns = document.getElementsByClassName("btn-color");
      for (let i = 0; i < colorBtns.length; i++) {
        colorBtns[i].classList.remove("btn-color-selected");
      }
      this.eventColor = "grey darken-1";
      this.errorMessage = "";
    },
    showDialog(event) {
      this.clearFields();
      if (event) {
        this.label = event.name;
        this.description = event.details;
        this.startDate = moment(new Date(event.start)).format("YYYY-MM-DDTHH:mm");
        this.endDate = moment(new Date(event.end)).format("YYYY-MM-DDTHH:mm");
        this.errorOnDateValues = false;
        this.eventColor = event.color;
      }
      this.eventId = event ? event.id : "";
      this.dialog = true;
    },
    onSubmit() {
      if (!this.label) {
        return;
      }
      const data = {
        label: this.label,
        description: this.description,
        startDate: new Date(this.startDate),
        endDate: new Date(this.endDate),
        color: this.eventColor,
      };
      axios
        .post(`${import.meta.env.VITE_BEMS_API_URL}/api/events${this.eventId ? `/${this.eventId}/edit` : ""}`, data)
        .then((response) => {
          if (response.status === 200) {
            this.putEventInCalendar(response.data);
            this.dialog = false;
            this.errorMessage = "";
            return;
          }
          this.errorMessage = `Error ${response.status}`;
        })
        .catch((error) => {
          this.errorMessage = error.message;
        });
    },
    checkDateFields() {
      const startValue = new Date(document.getElementById("startDateInput").value);
      const endValue = new Date(document.getElementById("endDateInput").value);
      this.errorOnDateValues = endValue <= startValue;
    },
    putEventInCalendar(event) {
      this.$parent.events = this.$parent.events.filter(function (value, index, arr) {
        return value.id !== event.id;
      });
      this.$parent.events.push({
        id: event.id,
        name: event.label,
        details: event.description,
        start: new Date(event.startDate),
        end: new Date(event.endDate),
        color: event.color,
        timed: true,
      });
    },
    selectEventColor(color) {
      this.eventColor = color;
      const colorBtns = document.getElementsByClassName("btn-color");
      for (let i = 0; i < colorBtns.length; i++) {
        colorBtns[i].classList.remove("btn-color-selected");
      }
      document.getElementById("btn-color-" + color).classList.add("btn-color-selected");
    }
  },
  mounted() {
    this.dialog = false;
    this.startDate = this.endDate = moment(new Date()).format("YYYY-MM-DDTHH:mm");
    const eventLabelMaxLength = import.meta.env.VITE_EVENT_LABEL_MAX_LENGTH;
    if (eventLabelMaxLength) this.eventLabelMaxLength = eventLabelMaxLength;
    const eventDescriptionMaxLength = import.meta.env.VITE_EVENT_DESCRIPTION_MAX_LENGTH;
    if (eventDescriptionMaxLength) this.eventDescriptionMaxLength = eventDescriptionMaxLength;
  },
};
</script>

<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <form @submit.prevent="onSubmit">
          <v-card-title>
            <span class="text-h5">Create an event</span>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12" v-if="errorMessage">
                  <v-alert
                    color="pink darken-1"
                    dark
                  >
                    {{ errorMessage }}
                  </v-alert>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="Label*" required v-model="label" :maxlength="eventLabelMaxLength" outlined counter></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-textarea label="Description" rows="3" v-model="description" :maxlength="eventDescriptionMaxLength" outlined counter></v-textarea>
                </v-col>
                <v-col cols="12" md="6">
                  <v-row>
                    <v-col cols="12">
                      <label>Start date*</label>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12">
                      <input type="datetime-local" required id="startDateInput" v-model="startDate" @change="checkDateFields" />
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="12" md="6">
                  <v-row>
                    <v-col cols="12">
                      <label>End date*</label>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12">
                      <input type="datetime-local" required id="endDateInput" v-model="endDate" @change="checkDateFields" />
                    </v-col>
                  </v-row>
                </v-col>
                <v-col cols="12">
                  <small v-if="errorOnDateValues" style="color: red">End date must be after start date.</small>
                </v-col>
                <v-col cols="12">
                  <v-row>
                    <v-col cols="12">
                      <label>Event color</label>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-btn
                      class="mx-2 btn-color"
                      fab
                      dark
                      small
                      v-for="color in colors"
                      v-bind:key="color"
                      :color="color"
                      @click="selectEventColor(color)"
                      :id="'btn-color-' + color"
                    />
                  </v-row>
                </v-col>
                <input type="hidden" v-model="eventId" />
              </v-row>
            </v-container>
            <br />
            <small>*indicates required field</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialog = false">
              Close
            </v-btn>
            <v-btn color="blue darken-1" text type="submit" :disabled="label === '' || errorOnDateValues">
              Save
            </v-btn>
          </v-card-actions>
        </form>
      </v-card>
    </v-dialog>
  </v-row>
</template>
