package ru.monopolio.quiz.telegram.entities

import com.google.gson.annotations.SerializedName

data class Message(
        @SerializedName("message_id") val messageId: Long,
        @SerializedName("from") val from: User?,
        @SerializedName("date") val date: Int,
        @SerializedName("chat") val chat: Chat,
        @SerializedName("forward_from") val forwardFrom: User?,
        @SerializedName("forward_from_chat") val forwardFromChat: Chat?,
        @SerializedName("forward_date") val forwardDate: Int?,
        @SerializedName("reply_to_message") val replyToMessage: Message?,
        @SerializedName("edit_date") val editDate: Int?,
        @SerializedName("text") val text: String?,
        @SerializedName("new_chat_member") val newChatMember: User?,
        @SerializedName("left_chat_member") val leftChatMember: User?,
        @SerializedName("new_chat_title") val newChatTitle: String?,
        @SerializedName("delete_chat_photo") val deleteChatPhoto: Boolean,
        @SerializedName("group_chat_created") val groupChatCreated: Boolean,
        @SerializedName("supergroup_chat_created") val supergroupChatCreated: Boolean,
        @SerializedName("channel_chat_created") val channelChatCreated: Boolean,
        @SerializedName("migrate_to_chat_id") val migrateToChatId: Boolean,
        @SerializedName("migrate_from_chat_id") val migrateFromChatId: Boolean,
        @SerializedName("pinned_message") val pinnedMessage: Message
)