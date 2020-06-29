package jp.katana.server.actor.data

class ActorDataIds {
    companion object {
        const val DATA_FLAGS = 0
        const val DATA_HEALTH = 1 //int (minecart/boat)

        const val DATA_VARIANT = 2 //int

        const val DATA_COLOR = 3 //byte

        const val DATA_NAME_TAG = 4 //string

        const val DATA_OWNER_EID = 5 //long

        const val DATA_TARGET_EID = 6 //long

        const val DATA_AIR = 7 //short

        const val DATA_POTION_COLOR = 8 //int (ARGB!)

        const val DATA_POTION_AMBIENT = 9 //byte

        const val DATA_JUMP_DURATION = 10 //long

        const val DATA_HURT_TIME = 11 //int (minecart/boat)

        const val DATA_HURT_DIRECTION = 12 //int (minecart/boat)

        const val DATA_PADDLE_TIME_LEFT = 13 //float

        const val DATA_PADDLE_TIME_RIGHT = 14 //float

        const val DATA_EXPERIENCE_VALUE = 15 //int (xp orb)

        const val DATA_DISPLAY_ITEM = 16 //int (id | (data << 16))

        const val DATA_DISPLAY_OFFSET = 17 //int

        const val DATA_HAS_DISPLAY = 18 //byte (must be 1 for minecart to show block inside)

        const val DATA_SWELL = 19
        const val DATA_OLD_SWELL = 20
        const val DATA_SWELL_DIR = 21
        const val DATA_CHARGE_AMOUNT = 22
        const val DATA_ENDERMAN_HELD_RUNTIME_ID = 23 //short

        const val DATA_ENTITY_AGE = 24 //short

        const val DATA_PLAYER_FLAGS = 26 //byte

        const val DATA_PLAYER_INDEX = 27
        const val DATA_PLAYER_BED_POSITION = 28 //block coords

        const val DATA_FIREBALL_POWER_X = 29 //float

        const val DATA_FIREBALL_POWER_Y = 30
        const val DATA_FIREBALL_POWER_Z = 31
        const val DATA_AUX_POWER = 32
        const val DATA_FISH_X = 33
        const val DATA_FISH_Z = 34
        const val DATA_FISH_ANGLE = 35
        const val DATA_POTION_AUX_VALUE = 36 //short

        const val DATA_LEAD_HOLDER_EID = 37 //long

        const val DATA_SCALE = 38 //float

        const val DATA_INTERACTIVE_TAG = 39 //string (button text)

        const val DATA_NPC_SKIN_ID = 40 //string

        const val DATA_URL_TAG = 41 //string

        const val DATA_MAX_AIR = 42 //short

        const val DATA_MARK_VARIANT = 43 //int

        const val DATA_CONTAINER_TYPE = 44 //byte

        const val DATA_CONTAINER_BASE_SIZE = 45 //int

        const val DATA_CONTAINER_EXTRA_SLOTS_PER_STRENGTH = 46 //int

        const val DATA_BLOCK_TARGET = 47 //block coords (ender crystal)

        const val DATA_WITHER_INVULNERABLE_TICKS = 48 //int

        const val DATA_WITHER_TARGET_1 = 49 //long

        const val DATA_WITHER_TARGET_2 = 50 //long

        const val DATA_WITHER_TARGET_3 = 51 //long

        const val DATA_AERIAL_ATTACK = 52
        const val DATA_BOUNDING_BOX_WIDTH = 53 //float

        const val DATA_BOUNDING_BOX_HEIGHT = 54 //float

        const val DATA_FUSE_LENGTH = 55 //int

        const val DATA_RIDER_SEAT_POSITION = 56 //vector3f

        const val DATA_RIDER_ROTATION_LOCKED = 57 //byte

        const val DATA_RIDER_MAX_ROTATION = 58 //float

        const val DATA_RIDER_MIN_ROTATION = 59 //float

        const val DATA_AREA_EFFECT_CLOUD_RADIUS = 60 //float

        const val DATA_AREA_EFFECT_CLOUD_WAITING = 61 //int

        const val DATA_AREA_EFFECT_CLOUD_PARTICLE_ID = 62 //int

        const val DATA_SHULKER_PEEK_ID = 63 //int

        const val DATA_SHULKER_ATTACH_FACE = 64 //byte

        const val DATA_SHULKER_ATTACHED = 65 //short

        const val DATA_SHULKER_ATTACH_POS = 66 //block coords

        const val DATA_TRADING_PLAYER_EID = 67 //long

        const val DATA_TRADING_CAREER = 68
        const val DATA_HAS_COMMAND_BLOCK = 69
        const val DATA_COMMAND_BLOCK_COMMAND = 70 //string

        const val DATA_COMMAND_BLOCK_LAST_OUTPUT = 71 //string

        const val DATA_COMMAND_BLOCK_TRACK_OUTPUT = 72 //byte

        const val DATA_CONTROLLING_RIDER_SEAT_NUMBER = 73 //byte

        const val DATA_STRENGTH = 74 //int

        const val DATA_MAX_STRENGTH = 75 //int

        const val DATA_SPELL_CASTING_COLOR = 76 //int

        const val DATA_LIMITED_LIFE = 77
        const val DATA_ARMOR_STAND_POSE_INDEX = 78 // int

        const val DATA_ENDER_CRYSTAL_TIME_OFFSET = 79 // int

        const val DATA_ALWAYS_SHOW_NAME_TAG = 80 // byte

        const val DATA_COLOR_2 = 81 // byte

        const val DATA_NAME_AUTHOR = 82
        const val DATA_SCORE_TAG = 83 //String

        const val DATA_BALLOON_ATTACHED_ENTITY = 84 // long

        const val DATA_PUFFER_FISH_SIZE = 85
        const val DATA_BUBBLE_TIME = 86
        const val DATA_AGENT = 87
        const val DATA_SITTING_AMOUNT = 88
        const val DATA_SITTING_AMOUNT_PREVIOUS = 89
        const val DATA_EATING_COUNTER = 90
        const val DATA_FLAGS_EXTENDED = 91
        const val DATA_LAYING_AMOUNT = 92
        const val DATA_LAYING_AMOUNT_PREVIOUS = 93
        const val DATA_DURATION = 94
        const val DATA_SPAWN_TIME = 95
        const val DATA_CHANGE_RATE = 96
        const val DATA_CHANGE_ON_PICKUP = 97
        const val DATA_PICKUP_COUNT = 98
        const val DATA_INTERACT_TEXT = 99
        const val DATA_TRADE_TIER = 100
        const val DATA_MAX_TRADE_TIER = 101
        const val DATA_TRADE_EXPERIENCE = 102
        const val DATA_SKIN_ID = 103 // int ???

        const val DATA_SPAWNING_FRAMES = 104
        const val DATA_COMMAND_BLOCK_TICK_DELAY = 105
        const val DATA_COMMAND_BLOCK_EXECUTE_ON_FIRST_TICK = 106
        const val DATA_AMBIENT_SOUND_INTERVAL = 107
        const val DATA_AMBIENT_SOUND_INTERVAL_RANGE = 108
        const val DATA_AMBIENT_SOUND_EVENT_NAME = 109
        const val DATA_FALL_DAMAGE_MULTIPLIER = 110
        const val DATA_NAME_RAW_TEXT = 111
        const val DATA_CAN_RIDE_TARGET = 112
        const val DATA_LOW_TIER_CURED_DISCOUNT = 113
        const val DATA_HIGH_TIER_CURED_DISCOUNT = 114
        const val DATA_NEARBY_CURED_DISCOUNT = 115
        const val DATA_NEARBY_CURED_DISCOUNT_TIMESTAMP = 116
        const val DATA_HIT_BOX = 117
        const val DATA_IS_BUOYANT = 118
        const val DATA_BUOYANCY_DATA = 119
    }
}