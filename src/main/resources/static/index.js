import {ChannelManager} from './modules/ChannelManager.js';
import {MessageManager} from './modules/MessageManager.js';
import ChannelCreateModal from "./modules/ChannelCreateModal.js";
import DMChannelCreateModal from "./modules/DMChannelCreateModal.js";
import UserProfileModal from "./modules/UserProfileModal.js";
import LoginModal from "./modules/LoginModal.js";

const users = [
    {
        id: "fb67f408-7b15-4be6-bc8c-5a3cfc7a8d92",
        username: "buzz",
        email: "buzz@toystory.com",
        avatarUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_LmKIb-v_c17dYbP7ifz9O2XkCnL0x_53Cw&s",
        createdAt: 1732237131588
    },
    {
        id: "2cab0b28-0b39-4031-8fe4-52f7290a394b",
        username: "woody",
        email: "woody@toystory.com",
        avatarUrl: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRpX3WlcgO0PCqjeWjOwKkDFsLCy0QaDg7Arw&s",
        createdAt: 1732237131577
    }
]

let currentUser = users[0]



// Initialize managers
const channelManager = new ChannelManager(currentUser.id);
const messageManager = new MessageManager(currentUser.id);
LoginModal();
ChannelCreateModal();
DMChannelCreateModal();
UserProfileModal();
// MessageStub();
