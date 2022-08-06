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
    disableSubmitBtn: true,
    eventLabelMaxLength: 32,
  }),
  methods: {
    showDialog() {
      this.dialog = true;
    },
    onSubmit() {
      if (!this.label) {
        return;
      }
      axios.post(`${import.meta.env.VITE_BEMS_API_URL}/api/events`, {
        label: this.label,
        description: this.description,
        startDate: new Date(this.startDate),
        endDate: new Date(this.endDate)
      });
    }
  },
  mounted() {
    this.dialog = false;
    this.startDate = this.endDate = moment(new Date()).format("YYYY-MM-DDTHH:mm");
    const eventLabelMaxLength = import.meta.env.VITE_EVENT_LABEL_MAX_LENGTH;
    if (eventLabelMaxLength) this.eventLabelMaxLength = eventLabelMaxLength;
  }
};
</script>

<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <form @submit="onSubmit">
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
                  <v-textarea label="Description" rows="3" v-model="description" outlined></v-textarea>
                </v-col>
                <v-col cols="12" md="6">
                  <v-row>
                    <v-col cols="12">
                      <label>Start date*</label>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="12">
                      <input type="datetime-local" required v-model="startDate" />
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
                      <input type="datetime-local" required v-model="endDate" />
                    </v-col>
                  </v-row>
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
            <v-btn color="blue darken-1" text @click="dialog = false" type="submit" :disabled="label === ''">
              Save
            </v-btn>
          </v-card-actions>
        </form>
      </v-card>
    </v-dialog>
  </v-row>
</template>
