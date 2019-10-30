package kz.smart.calendar.models.enums

enum class ErrorCodeTypeDescription(val value: String)
{
    invalid_category_status("invalid_category_status"),

    invalid_subcategory_status("invalid_subcategory_status"),

    invalid_option_status("invalid_option_status"),

    event_not_exist("event_not_exist"),

    invalid_event_status("invalid_event_status"),

    incorrect_login_or_password("incorrect_login_or_password"),

    user_not_exist("user_not_exist"),

    session_not_exist("session_not_exist"),

    invalid_gender("invalid_gender"),

    wrong_date_format("wrong_date_format"),

    subcategories_with_category_exist("subcategories_with_category_exist"),

    events_with_category_exist("events_with_category_exist"),

    events_with_subcategory_exist("events_with_subcategory_exist"),

    category_not_exist("category_not_exist"),

    invalid_title("invalid_title"),

    invalid_color("invalid_color"),

    icon_("icon_"),

    subcategory_not_exist("subcategory_not_exist"),

    option_not_exist("option_not_exist"),

    access_denied("access_denied")
}