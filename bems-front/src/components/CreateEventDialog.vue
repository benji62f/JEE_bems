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
    errorOnDateValues: true,
    eventLabelMaxLength: 32,
    eventDescriptionMaxLength: 150,
  }),
  methods: {
    showDialog() {
      this.dialog = true;
    },
    onSubmit() {
      if (!this.label) {
        return;
      }
      axios
        .post(`${import.meta.env.VITE_BEMS_API_URL}/api/events`, {
          label: this.label,
          description: this.description,
          startDate: new Date(this.startDate),
          endDate: new Date(this.endDate),
        })
        .then((response) => {
          if (response.status === 200) {
            this.$parent.events.push({
              id: response.data.id,
              name: response.data.label,
              details: response.data.details,
              start: new Date(response.data.startDate),
              end: new Date(response.data.endDate),
              color: this.$parent.colors[this.$parent.rnd(0, this.$parent.colors.length - 1)],
              timed: true,
            });
          }
        });
    },
    checkDateFields() {
      const startValue = new Date(document.getElementById("startDateInput").value);
      const endValue = new Date(document.getElementById("endDateInput").value);
      this.errorOnDateValues = endValue <= startValue;
    },
  },
  mounted() {
    this.dialog = false;
    this.startDate = this.endDate = moment(new Date()).format("YYYY-MM-DDTHH:mm");
    const eventLabelMaxLength = import.meta.env.VITE_EVENT_LABEL_MAX_LENGTH;
    if (eventLabelMaxLength) this.eventLabelMaxLength = eventLabelMaxLength;
    const eventDescriptionMaxLength = import.meta.env.VITE_EVENT_DESCRIPTION_MAX_LENGTH;
    if (eventDescriptionMaxLength) this.eventDescriptionMaxLength = eventDescriptionMaxLength;
  }
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
              </v-row>
            </v-container>
            <small>*indicates required field</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="dialog = false">
              Close
            </v-btn>
            <v-btn color="blue darken-1" text @click="dialog = false" type="submit" :disabled="label === '' || errorOnDateValues">
              Save
            </v-btn>
          </v-card-actions>
        </form>
      </v-card>
    </v-dialog>
  </v-row>
</template>
