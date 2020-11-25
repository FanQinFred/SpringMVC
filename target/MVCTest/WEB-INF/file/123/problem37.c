#include <stdio.h> 
#include <stdlib.h>
#include <string.h>
#include <assert.h>
char* attach_header(char *buffer, char *header, int *length) {
    int len = strlen(buffer);
    int hlen = strlen(header);
    *length = len + hlen;
    char *buffer2 = (char*)malloc(*length + 1);
    memcpy(buffer2, header, hlen);
    memcpy(buffer2 + hlen, buffer, len);
    buffer2[*length] = '\0';
    return buffer2;
}
void physical_layer_send(char *buffer, int len) {
    char header[] = "physical_layer_header;";
    assert(strlen(buffer) == len);
    int length = 0;
    char *buffer2 = attach_header(buffer, header, &length);
    printf("%s\n", buffer2);
    free(buffer2);
}
void datalink_layer_send(char *buffer, int len) {
    char header[] = "datalink_layer_header;";
    assert(strlen(buffer) == len);
    int length = 0;
    char *buffer2 = attach_header(buffer, header, &length);
    printf("%s\n", buffer2);
    physical_layer_send(buffer2, length);
    free(buffer2);
}
void network_layer_send(char *buffer, int len) {
    char header[] = "network_layer_header;";
    assert(strlen(buffer) == len);
    int length = 0;
    char *buffer2 = attach_header(buffer, header, &length);
    printf("%s\n", buffer2);
    datalink_layer_send(buffer2, length);
    free(buffer2);
}
void transport_layer_send(char *buffer, int len) {
    char header[] = "transport_layer_header;";
    assert(strlen(buffer) == len);
    int length = 0;
    char *buffer2 = attach_header(buffer, header, &length);
    printf("%s\n", buffer2);
    network_layer_send(buffer2, length);
    free(buffer2);
}
void session_layer_send(char *buffer, int len) {
    char header[] = "session_layer_header;";
    assert(strlen(buffer) == len);
    int length = 0;
    char *buffer2 = attach_header(buffer, header, &length);
    printf("%s\n", buffer2);
    transport_layer_send(buffer2, length);
    free(buffer2);
}
void presentation_layer_send(char *buffer, int len) {
    char header[] = "presentation_layer_header;";
    assert(strlen(buffer) == len);
    int length = 0;
    char *buffer2 = attach_header(buffer, header, &length);
    printf("%s\n", buffer2);
    session_layer_send(buffer2, length);
    free(buffer2);
}
void application_layer_send(char *buffer, int len) {
    static char header[] = "application_layer_header;";
    assert(strlen(buffer) == len);
    int length = 0;
    char *buffer2 = attach_header(buffer, header, &length);
    printf("%s\n", buffer2);
    presentation_layer_send(buffer2, length);
    free(buffer2);
}
void send_message() {
    char message[] = "This is a test message!";
    application_layer_send(message, strlen(message));
}
int main(int argc, char *argv[]) {
    send_message();
    return 0;
}